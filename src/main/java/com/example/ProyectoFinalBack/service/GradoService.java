package com.example.ProyectoFinalBack.service;


import com.example.ProyectoFinalBack.model.Grado;
import com.example.ProyectoFinalBack.repository.GradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GradoService {

    @Autowired
    private GradoRepository repo;

    public List<Grado> listar() {
        return repo.findAll();
    }

    public Grado buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Grado registrar(Grado g) {
        return repo.save(g);
    }

}
