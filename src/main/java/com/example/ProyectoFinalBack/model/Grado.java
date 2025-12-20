package com.example.ProyectoFinalBack.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Grado")
public class Grado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGrado;

    @Column(nullable = false, length = 15)
    private String nombreGrado;

    // ======== GETTERS ========

    public Integer getIdGrado() {
        return idGrado;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    // ======== SETTERS ========

    public void setIdGrado(Integer idGrado) {
        this.idGrado = idGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

}