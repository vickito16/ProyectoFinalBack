package com.example.ProyectoFinalBack.service;


import com.example.ProyectoFinalBack.model.Concepto;
import com.example.ProyectoFinalBack.repository.ConceptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConceptoService {

    @Autowired
    private ConceptoRepository repo;

    public List<Concepto> listar() {
        return repo.findAll();
    }
}
