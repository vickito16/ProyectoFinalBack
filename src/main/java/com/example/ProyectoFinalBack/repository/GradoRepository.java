package com.example.ProyectoFinalBack.repository;


import com.example.ProyectoFinalBack.model.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradoRepository extends JpaRepository<Grado, Integer> {
    // JpaRepository ya trae findAll, findById, save, delete, etc.
}
