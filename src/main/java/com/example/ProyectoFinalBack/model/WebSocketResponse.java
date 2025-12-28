package com.example.ProyectoFinalBack.model;

public class WebSocketResponse {
    // Usaremos 'contenido' para evitar confusiones con 'mensaje'
    private String contenido;

    public WebSocketResponse() {
    }

    public WebSocketResponse(String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}

