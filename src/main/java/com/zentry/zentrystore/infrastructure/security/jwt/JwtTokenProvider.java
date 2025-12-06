package com.zentry.zentrystore.infrastructure.security.jwt;// com.unas.zentystore.infrastructure.security.jwt.JwtTokenProvider.java

// ... [Tu código actual]

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    // ... [Tu clave secreta y otros campos, si los tienes]

    public Long getUserIdFromToken(String token) {
        // ⚠️ HACK TEMPORAL: Esto permite que RatingController compile y use un ID fijo.
        // Debe ser reemplazado por la lógica de Jwts.parserBuilder() una vez que
        // las dependencias de 'io.jsonwebtoken' se resuelvan.
        return 1L;
    }

    // ... [otros métodos]
}