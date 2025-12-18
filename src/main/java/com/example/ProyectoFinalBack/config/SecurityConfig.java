package com.example.ProyectoFinalBack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    // Permitir acceso libre a todos los endpoints
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF para pruebas REST
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite todos los endpoints
                )
                .formLogin(login -> login.disable()) // Sin formulario de login
                .httpBasic(basic -> basic.disable()); // Sin autenticación básica
        return http.build();
    }

    // Bean para encriptar contraseñas (BCrypt)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

