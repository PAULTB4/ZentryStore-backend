package com.zentry.zentrystore.infrastructure.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Representa el usuario autenticado en el contexto de seguridad de Spring.
 * Permite acceder al userId directamente sin tener que extraerlo del token cada vez.
 */
public class UserPrincipal {

    private final Long userId;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long userId, String email, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.email = email;
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }

    /**
     * ✅ AGREGADO: Retorna el email del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * ✅ AGREGADO: Retorna el nombre/identificador principal (email en este caso)
     * Esto es lo que Spring Security espera de un Principal
     */
    public String getName() {
        return email;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Verifica si el usuario tiene un rol específico
     */
    public boolean hasRole(String roleName) {
        return authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals(roleName));
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}