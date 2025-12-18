package com.example.ProyectoFinalBack.dto;

public class mensajeDTO {
    private String resultado;
    private String mensaje;
    private String token;

    public mensajeDTO() {
    }

    public mensajeDTO(String resultado, String mensaje, String token) {
        this.resultado = resultado;
        this.mensaje = mensaje;
        this.token = token;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

