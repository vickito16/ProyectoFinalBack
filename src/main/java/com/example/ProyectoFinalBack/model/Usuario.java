package com.example.ProyectoFinalBack.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private int idUsuario;

    @Column(name = "logiUsua", nullable = false, length = 50, unique = true)
    private String logiUsua;

    @Column(name = "passUsua", length = 200)
    private String passUsua; // Puede ser null si se autentica vía OAuth

    // 🔹 CAMBIO IMPORTANTE: Mapeo del ENUM
    // Usamos STRING para que en la BD se guarde "SECRETARIA" o "DIRECTOR"
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    // 🔹 Campos para soporte OAuth 2.0
    @Column(name = "oauth_provider", length = 50)
    private String oauthProvider;

    @Column(name = "oauth_id", length = 150)
    private String oauthId;

    @Column(name = "access_token", columnDefinition = "TEXT")
    private String accessToken;

    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;

    @Column(name = "token_expiration")
    private LocalDateTime tokenExpiration;

    // 🔹 Constructores
    public Usuario() {}

    public Usuario(String logiUsua, String passUsua, Rol rol) {
        this.logiUsua = logiUsua;
        this.passUsua = passUsua;
        this.rol = rol;
    }

    // 🔹 Lógica para el valor por defecto (DEFAULT 'SECRETARIA')
    @PrePersist
    public void prePersist() {
        if (this.rol == null) {
            this.rol = Rol.SECRETARIA;
        }
    }

    // 🔹 Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogiUsua() {
        return logiUsua;
    }

    public void setLogiUsua(String logiUsua) {
        this.logiUsua = logiUsua;
    }

    public String getPassUsua() {
        return passUsua;
    }

    public void setPassUsua(String passUsua) {
        this.passUsua = passUsua;
    }

    // Getter y Setter para ROL
    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getOauthProvider() {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(LocalDateTime tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }
}

