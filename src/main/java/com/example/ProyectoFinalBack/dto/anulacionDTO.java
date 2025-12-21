package com.example.ProyectoFinalBack.dto;

import lombok.Data;

@Data
public class anulacionDTO {
    private Integer idRecibo;
    private Integer codigoDirector; // El código de 6 dígitos del Authenticator

    public anulacionDTO() {
    }

    public anulacionDTO(Integer idRecibo, Integer codigoDirector) {
        this.idRecibo = idRecibo;
        this.codigoDirector = codigoDirector;
    }

    public Integer getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(Integer idRecibo) {
        this.idRecibo = idRecibo;
    }

    public Integer getCodigoDirector() {
        return codigoDirector;
    }

    public void setCodigoDirector(Integer codigoDirector) {
        this.codigoDirector = codigoDirector;
    }
}
