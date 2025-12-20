package com.example.ProyectoFinalBack.repository;


import com.example.ProyectoFinalBack.model.Concepto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConceptoRepository extends JpaRepository<Concepto, Integer> {
    // JpaRepository ya nos da findAll()
}