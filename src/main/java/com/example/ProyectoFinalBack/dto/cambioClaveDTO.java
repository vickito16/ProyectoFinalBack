package com.example.ProyectoFinalBack.dto;

public class cambioClaveDTO {
    private String login;
    private String claveActual;
    private String nuevaClave;
    private String confirmaClave;

    // Getters y Setters
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getClaveActual() { return claveActual; }
    public void setClaveActual(String claveActual) { this.claveActual = claveActual; }

    public String getNuevaClave() { return nuevaClave; }
    public void setNuevaClave(String nuevaClave) { this.nuevaClave = nuevaClave; }

    public String getConfirmaClave() { return confirmaClave; }
    public void setConfirmaClave(String confirmaClave) { this.confirmaClave = confirmaClave; }
}
