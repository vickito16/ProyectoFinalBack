package com.example.ProyectoFinalBack.repository;


import com.example.ProyectoFinalBack.model.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Integer> {

    // Obtenemos el número más alto registrado hasta el momento
    // Si la tabla está vacía, devolverá null
    @Query("SELECT MAX(r.numero) FROM Recibo r")
    Integer obtenerUltimoNumero();
}
