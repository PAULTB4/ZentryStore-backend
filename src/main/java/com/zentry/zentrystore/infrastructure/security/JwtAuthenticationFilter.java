package com.zentry.zentrystore.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.zentry.zentrystore.infrastructure.security.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Si no hay header Authorization o no empieza con "Bearer ", continuar sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extraer el token (después de "Bearer ")
            final String jwt = authHeader.substring(7);

            // Extraer email del token
            final String userEmail = jwtService.extractEmail(jwt);

            // Si hay email y no hay autenticación previa
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Validar que el token sea válido
                if (jwtService.isTokenValid(jwt)) {

                    // ✅ Extraer roles del token
                    List<String> roles = jwtService.extractRoles(jwt);
                    Long userId = jwtService.extractUserId(jwt);

                    logger.debug("Token válido para usuario: {} (ID: {}), roles: {}", userEmail, userId, roles);

                    // ✅ Convertir roles a GrantedAuthority
                    List<GrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    // ✅ Crear objeto principal personalizado con userId
                    UserPrincipal principal = new UserPrincipal(userId, userEmail, authorities);

                    // ✅ Crear token de autenticación
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            principal,
                            null,
                            authorities
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // ✅ Establecer autenticación en el contexto
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    logger.debug("Usuario autenticado exitosamente: {}", userEmail);
                }
            }
        } catch (Exception e) {
            logger.error("Error al procesar JWT: {}", e.getMessage());
            // No lanzar excepción, solo continuar sin autenticar
            // Spring Security manejará el acceso no autorizado
        }

        filterChain.doFilter(request, response);
    }
}