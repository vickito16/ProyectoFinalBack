package com.example.ProyectoFinalBack.repository;


import com.example.ProyectoFinalBack.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {

    // 1. Buscar matrículas por DNI del Alumno
    // Al ser una relación, navegamos: Alumno -> DniAlumno
    List<Matricula> findByAlumno_DniAlumno(String dniAlumno);

    // 2. Listar por Año Académico
    List<Matricula> findByAnioAcademico(String anioAcademico);

    // 3. Listar por ID de Grado
    List<Matricula> findByGrado_IdGrado(Integer idGrado);

    // 4. Filtrado combinado (Año Y Grado)
    // Esto llena la tabla cuando seleccionan ambos combos en tu interfaz
    List<Matricula> findByAnioAcademicoAndGrado_IdGrado(String anioAcademico, Integer idGrado);

    // Validar si el alumno (por ID) ya tiene matrícula en ese año
    boolean existsByAlumno_IdAlumnoAndAnioAcademico(Integer idAlumno, String anioAcademico);
}
