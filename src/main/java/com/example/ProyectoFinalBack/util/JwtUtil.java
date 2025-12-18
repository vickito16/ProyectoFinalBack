package com.example.ProyectoFinalBack.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    // 🔑 Clave secreta para firmar los tokens
    private final String SECRET_KEY = "PINGUINO_EL_MAS_ANTARTICO_COMO_SKIPER_NO_FACTOS_POR_LA_LLAMAS";

    // 🔹 Duración del token (por ejemplo 2 horas)
    private final long EXPIRATION_TIME = 2 * 60 * 60 * 1000; // Esto es vital

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ================================
    // 🔹 1️⃣ Generar un token
    // ================================
    public String generarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ================================
    // 🔹 2️⃣ Obtener usuario (subject)
    // ================================
    public String obtenerUsuarioDelToken(String token) {
        return obtenerClaims(token).getSubject();
    }

    // ================================
    // 🔹 3️⃣ Validar token
    // ================================
    public boolean validarToken(String token, String username) {
        try {
            final String usuario = obtenerUsuarioDelToken(token);
            return (usuario.equals(username) && !estaExpirado(token));
        } catch (Exception e) {
            return false;
        }
    }
    // ================================
    // 🔹 4️⃣ Obtener claims
    // ================================
    private Claims obtenerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ================================
    // 🔹 5️⃣ Verificar expiración
    // ================================
    private boolean estaExpirado(String token) {
        Date expiracion = obtenerClaims(token).getExpiration();
        return expiracion.before(new Date());
    }
}

