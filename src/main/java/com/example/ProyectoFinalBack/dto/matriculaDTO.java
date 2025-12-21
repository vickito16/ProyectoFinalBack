package com.example.ProyectoFinalBack.dto;

import lombok.Data;

@Data
public class matriculaDTO {
    private String idAlumno;     // Búsqueda por DNI
    private Integer idGrado;      // Seleccionado de un combo
    private String observacion;
    private String anioAcademico; // Ej: "2025"

    public matriculaDTO() {
    }

    public matriculaDTO(String idAlumno, Integer idGrado, String observacion, String anioAcademico) {
        this.idAlumno = idAlumno;
        this.idGrado = idGrado;
        this.observacion = observacion;
        this.anioAcademico = anioAcademico;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Integer getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(Integer idGrado) {
        this.idGrado = idGrado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getAnioAcademico() {
        return anioAcademico;
    }

    public void setAnioAcademico(String anioAcademico) {
        this.anioAcademico = anioAcademico;
    }
}

