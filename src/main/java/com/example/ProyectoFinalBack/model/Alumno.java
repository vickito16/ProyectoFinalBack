package com.example.ProyectoFinalBack.model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "alumno")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdAlumno")
    private Integer idAlumno;

    @Column(name = "NombreAlumno", nullable = false, length = 250)
    private String nombreAlumno;

    @Column(name = "ApePaterAlumno", nullable = false, length = 250)
    private String apePaterAlumno;

    @Column(name = "ApeMaterAlumno", nullable = false, length = 250)
    private String apeMaterAlumno;

    @Column(name = "DNIAlumno", nullable = false, length = 8)
    private String dniAlumno;

    @Column(name = "DireccionAlumno", length = 50)
    private String direccionAlumno;

    @Column(name = "TelefonoApoderado", length = 15)
    private String telefonoApoderado;

    @Column(name = "FechaNacimiento")
    private Date fechaNacimiento;

    @Column(name = "estado")
    private Integer estado = 1;

    // ====== Getters y Setters ======

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getApePaterAlumno() {
        return apePaterAlumno;
    }

    public void setApePaterAlumno(String apePaterAlumno) {
        this.apePaterAlumno = apePaterAlumno;
    }

    public String getApeMaterAlumno() {
        return apeMaterAlumno;
    }

    public void setApeMaterAlumno(String apeMaterAlumno) {
        this.apeMaterAlumno = apeMaterAlumno;
    }

    public String getDniAlumno() {
        return dniAlumno;
    }

    public void setDniAlumno(String dniAlumno) {
        this.dniAlumno = dniAlumno;
    }

    public String getDireccionAlumno() {
        return direccionAlumno;
    }

    public void setDireccionAlumno(String direccionAlumno) {
        this.direccionAlumno = direccionAlumno;
    }

    public String getTelefonoApoderado() {
        return telefonoApoderado;
    }

    public void setTelefonoApoderado(String telefonoApoderado) {
        this.telefonoApoderado = telefonoApoderado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
