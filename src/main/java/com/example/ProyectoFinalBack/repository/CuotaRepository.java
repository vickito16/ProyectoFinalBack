package com.example.ProyectoFinalBack.repository;


import com.example.ProyectoFinalBack.model.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Integer> {
    // Metodo útil para ver las deudas de una matrícula específica
    List<Cuota> findByMatricula_IdMatricula(Integer idMatricula);

    // Buscar por ID de Recibo (para anular)
    List<Cuota> findByIdRecibo(Integer idRecibo);
}
