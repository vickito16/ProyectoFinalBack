package com.example.ProyectoFinalBack.model;

import jakarta.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "Matricula")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMatricula;

    @Column(nullable = false)
    private Date fechaRegistro;

    @Column(length = 100)
    private String observacion;

    @Column(nullable = false, length = 4)
    private String anioAcademico;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "IdAlumno", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "IdGrado", nullable = false)
    private Grado grado;

    // ==========================================
    // Constructor Vacío (Obligatorio para JPA)
    // ==========================================
    public Matricula() {
    }

    // ==========================================
    // Getters y Setters Manuales
    // ==========================================

    public Integer getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Integer idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    // ==========================================
    // PrePersist (Lógica automática)
    // ==========================================
    @PrePersist
    public void prePersist() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = Date.valueOf(LocalDate.now());
        }
    }
}
