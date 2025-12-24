package com.example.ProyectoFinalBack.repository;


import com.example.ProyectoFinalBack.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {

    // Validar si el alumno (por ID) ya tiene matrícula en ese año
    boolean existsByAlumno_IdAlumnoAndAnioAcademico(Integer idAlumno, String anioAcademico);
}
