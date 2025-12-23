package com.example.ProyectoFinalBack.service;

import com.example.ProyectoFinalBack.model.Matricula;
import com.example.ProyectoFinalBack.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository repo;

    public List<Matricula> listar() {
        return repo.findAll();
    }

    public Matricula registrar(Matricula m) {
        return repo.save(m);
    }

    public boolean existeMatricula(Integer idAlumno, String anio) {
        return repo.existsByAlumno_IdAlumnoAndAnioAcademico(idAlumno, anio);
    }
}
