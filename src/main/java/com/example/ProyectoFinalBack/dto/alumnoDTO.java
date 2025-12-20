package com.example.ProyectoFinalBack.dto;
public class alumnoDTO {
    private Integer idAlumno;
    private String nombreAlumno;
    private String apePaterAlumno;
    private String apeMaterAlumno;
    private String dniAlumno;
    private String direccionAlumno;
    private String telefonoApoderado;
    private String fechaNacimiento;
    private Integer estado;

    public alumnoDTO() {
    }

    public alumnoDTO(Integer idAlumno, String nombreAlumno, String apePaterAlumno, String apeMaterAlumno, String dniAlumno, String direccionAlumno, String telefonoApoderado, String fechaNacimiento, Integer estado) {
        this.idAlumno = idAlumno;
        this.nombreAlumno = nombreAlumno;
        this.apePaterAlumno = apePaterAlumno;
        this.apeMaterAlumno = apeMaterAlumno;
        this.dniAlumno = dniAlumno;
        this.direccionAlumno = direccionAlumno;
        this.telefonoApoderado = telefonoApoderado;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }

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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}