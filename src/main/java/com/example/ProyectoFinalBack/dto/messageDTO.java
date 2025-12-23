package com.example.ProyectoFinalBack.dto;

public class messageDTO {
    private String loEnvia;
    private String mensaje;
    private String targetUsername; // Nombre del usuario destino (ej: "Soporte")

    // Getters y Setters
    public String getLoEnvia() { return loEnvia; }
    public void setLoEnvia(String loEnvia) { this.loEnvia = loEnvia; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getTargetUsername() { return targetUsername; }
    public void setTargetUsername(String targetUsername) { this.targetUsername = targetUsername; }
}
