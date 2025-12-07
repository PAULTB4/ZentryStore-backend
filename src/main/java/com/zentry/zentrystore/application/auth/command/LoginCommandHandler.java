package com.zentry.zentrystore.application.auth.command;

import com.zentry.zentrystore.application.user.dto.response.AuthResponse;
import com.zentry.zentrystore.domain.user.model.User;
import com.zentry.zentrystore.domain.user.model.UserRole;
import com.zentry.zentrystore.domain.user.repository.UserRepository;
import com.zentry.zentrystore.infrastructure.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class LoginCommandHandler {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; // ✅ AGREGADO

    public LoginCommandHandler(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) { // ✅ AGREGADO
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService; // ✅ AGREGADO
    }

    @Transactional
    public AuthResponse handle(LoginCommand command) {
        // Buscar usuario por email
        User user = userRepository.findByEmail(command.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        // Verificar que el usuario esté activo
        if (!user.getActive()) {
            throw new BadCredentialsException("Account is inactive");
        }

        // Verificar contraseña
        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        // Actualizar último login
        user.updateLastLogin();
        userRepository.save(user);

        // ✅ GENERAR TOKEN JWT REAL
        String token = jwtService.generateToken(user);

        // Crear respuesta
        AuthResponse response = new AuthResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRoles(user.getRoles().stream()
                .map(UserRole::getName)
                .collect(Collectors.toList()));
        response.setToken(token); // ✅ TOKEN REAL

        return response;
    }
}