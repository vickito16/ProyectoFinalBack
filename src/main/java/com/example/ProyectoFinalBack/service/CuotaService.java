package com.example.ProyectoFinalBack.service;


import com.example.ProyectoFinalBack.model.Concepto;
import com.example.ProyectoFinalBack.model.Cuota;
import com.example.ProyectoFinalBack.model.Matricula;
import com.example.ProyectoFinalBack.repository.ConceptoRepository;
import com.example.ProyectoFinalBack.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CuotaService {

    @Autowired
    private CuotaRepository repo;

    @Autowired
    private ConceptoRepository conceptoRepo;

    public List<Cuota> listarPorMatricula(Integer idMatricula) {
        return repo.findByMatricula_IdMatricula(idMatricula);
    }

    // Metodo para guardar una lista masiva de cuotas
    public List<Cuota> guardarLote(List<Cuota> cuotas) {
        return repo.saveAll(cuotas);
    }

    // --- AGREGAR ESTOS MÉTODOS ---

    // 1. Buscar una cuota individual para poder pagarla
    public Cuota buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    // 2. Guardar/Actualizar una cuota individual
    public Cuota guardar(Cuota c) {
        return repo.save(c);
    }

    public List<Cuota> buscarPorIds(List<Integer> ids) {
        return repo.findAllById(ids);
    }

    // =======================================================
    // 🔹 NUEVO METODO: Generar cuotas automáticamente
    // =======================================================
    public void generarCuotasParaMatricula(Matricula matricula) {
        List<Concepto> conceptos = conceptoRepo.findAll();
        List<Cuota> cuotasGeneradas = new ArrayList<>();

        for (Concepto c : conceptos) {
            Cuota nuevaCuota = new Cuota();
            nuevaCuota.setMatricula(matricula);
            nuevaCuota.setConcepto(c);
            nuevaCuota.setEstadoPago(0); // 0: Debe

            // Lógica de precios trasladada desde el Controller
            if (c.getIdConcepto() == 1) {
                // Concepto 1: Matrícula
                nuevaCuota.setPrecio(new BigDecimal("300.00"));
            } else {
                // Resto: Mensualidades
                nuevaCuota.setPrecio(new BigDecimal("500.00"));
            }

            cuotasGeneradas.add(nuevaCuota);
        }

        repo.saveAll(cuotasGeneradas);
    }
}
