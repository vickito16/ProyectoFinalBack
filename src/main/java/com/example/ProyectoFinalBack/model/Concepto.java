package com.example.ProyectoFinalBack.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Concepto")
public class Concepto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConcepto;

    @Column(nullable = false, length = 30)
    private String nombreConcepto;

    // --------------------------
    // Getters y Setters
    // --------------------------

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getNombreConcepto() {
        return nombreConcepto;
    }

    public void setNombreConcepto(String nombreConcepto) {
        this.nombreConcepto = nombreConcepto;
    }
}

