package com.example.ProyectoFinalBack.repository;


import com.example.ProyectoFinalBack.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

    // 🔹 Buscar alumno por DNI (campo dniAlum)
    Optional<Alumno> findByDniAlumno(String dniAlumno);
}
