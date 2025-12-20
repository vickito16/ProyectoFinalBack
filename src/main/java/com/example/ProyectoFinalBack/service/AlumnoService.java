package com.example.ProyectoFinalBack.service;


import com.example.ProyectoFinalBack.model.Alumno;
import com.example.ProyectoFinalBack.repository.AlumnoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository repo;

    public List<Alumno> listar() {
        return repo.findAll();
    }

    public Alumno buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Alumno registrar(Alumno a) {
        return repo.save(a);
    }

    public Alumno buscarPorDni(String dniAlum) {
        return repo.findByDniAlumno(dniAlum).orElse(null);
    }

    // El actualizar recibe el objeto completo, igual que tu ejemplo ProductoService
    public Alumno actualizar(Alumno a) {
        return repo.save(a);
    }

    public void eliminar(Alumno a) {
        repo.save(a);
    }
}
