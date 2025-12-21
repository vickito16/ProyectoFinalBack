package com.example.ProyectoFinalBack.dto;


import com.example.ProyectoFinalBack.model.Matricula;
import lombok.Data;

@Data
public class infoMatriculaDTO {
    private int idMatricula;
    private String dniAlumno;
    private String nombresAlumno;
    private String apePatAlumno;
    private String apeMatAlumno;
    private String nombreGrado;
    private String anioAcademico;
    private String telefonoApoderado; // Nuevo Campo
    private Integer estado;

    public infoMatriculaDTO() {
    }

    public infoMatriculaDTO(Matricula m) {
        this.idMatricula = m.getIdMatricula();
        this.dniAlumno = m.getAlumno().getDniAlumno();
        this.nombresAlumno = m.getAlumno().getNombreAlumno();
        this.apePatAlumno = m.getAlumno().getApePaterAlumno();
        this.apeMatAlumno = m.getAlumno().getApeMaterAlumno();
        this.nombreGrado = m.getGrado().getNombreGrado();
        this.anioAcademico = m.getAnioAcademico();

        // 🚨 IMPORTANTE: Faltaba esta línea para llenar el dato
        this.telefonoApoderado = m.getAlumno().getTelefonoApoderado();

        this.estado = Integer.valueOf(m.getAlumno().getEstado());
    }
}