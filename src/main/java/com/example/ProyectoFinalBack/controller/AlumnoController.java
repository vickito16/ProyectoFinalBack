package com.example.ProyectoFinalBack.controller;


import com.example.ProyectoFinalBack.dto.alumnoDTO;
import com.example.ProyectoFinalBack.dto.mensajeDTO;
import com.example.ProyectoFinalBack.model.Alumno;
import com.example.ProyectoFinalBack.service.AlumnoService;
import com.example.ProyectoFinalBack.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/alumnos")
@CrossOrigin(origins = "*")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private JwtUtil jwtUtil;

    // =========================================
    // 🔹 Validar token desde header
    // =========================================
    private boolean validarToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String usuario = jwtUtil.obtenerUsuarioDelToken(token);
            return jwtUtil.validarToken(token, usuario);
        }
        return false;
    }

    // =========================================
    // 🔹 Listar Alumnos
    // =========================================
    @GetMapping("/listar")
    public ResponseEntity<?> listar(@RequestHeader("Authorization") String header) {

        if (!validarToken(header)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido o expirado");
        }

        List<Alumno> lista = alumnoService.listar();
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/buscarDNI/{dniAlum}")
    public Alumno buscarPorDni(
            @RequestHeader("Authorization") String header,
            @PathVariable String dniAlum) {

        if (!validarToken(header)) {
            // ❌ No puedes devolver ResponseEntity si el retorno es Alumno
            // ✔ Debes lanzar una excepción
            throw new RuntimeException("Token inválido o expirado");
        }

        Alumno alumno = alumnoService.buscarPorDni(dniAlum);

        if (alumno == null) {
            throw new RuntimeException("Alumno no encontrado con DNI: " + dniAlum);
        }

        return alumno; // ✔ Retorna el objeto COMPLETO tal como lo deseas
    }



    // =========================================
    // 🔹 Registrar Alumno
    // =========================================
    @PostMapping("/registrar")
    public mensajeDTO registrar(@RequestHeader("Authorization") String header,
                                @RequestBody alumnoDTO a) {

        if (!validarToken(header)) {
            return new mensajeDTO("error", "token Invalido", null);
        }

        Alumno nuevo = new Alumno();
        nuevo.setNombreAlumno(a.getNombreAlumno());
        nuevo.setApePaterAlumno(a.getApePaterAlumno());
        nuevo.setApeMaterAlumno(a.getApeMaterAlumno());
        nuevo.setDniAlumno(a.getDniAlumno());
        nuevo.setDireccionAlumno(a.getDireccionAlumno());
        nuevo.setTelefonoApoderado(a.getTelefonoApoderado());

        if (a.getFechaNacimiento() != null)
            nuevo.setFechaNacimiento(Date.valueOf(a.getFechaNacimiento()));

        nuevo.setEstado(a.getEstado() != null ? a.getEstado() : 1);

        alumnoService.registrar(nuevo);

        return new mensajeDTO("ok", "Alumno registrado", null);
    }

    // =========================================
    // 🔹 Actualizar Alumno
    // =========================================
    @PostMapping("/update")
    public mensajeDTO actualizar(@RequestHeader("Authorization") String header,
                                 @RequestBody alumnoDTO a) {

        if (!validarToken(header)) {
            return new mensajeDTO("error", "token Invalido", null);
        }

        Alumno existente = alumnoService.buscarPorId(a.getIdAlumno());

        if (existente == null) {
            return new mensajeDTO("error", "Alumno no encontrado", null);
        }

        existente.setNombreAlumno(a.getNombreAlumno());
        existente.setApePaterAlumno(a.getApePaterAlumno());
        existente.setApeMaterAlumno(a.getApeMaterAlumno());
        existente.setDniAlumno(a.getDniAlumno());
        existente.setDireccionAlumno(a.getDireccionAlumno());
        existente.setTelefonoApoderado(a.getTelefonoApoderado());

        if (a.getFechaNacimiento() != null)
            existente.setFechaNacimiento(Date.valueOf(a.getFechaNacimiento()));

        existente.setEstado(a.getEstado());

        alumnoService.actualizar(existente);

        return new mensajeDTO("ok", "Alumno actualizado exitosamente", null);
    }

    // =========================================
    // 🔹 Eliminar Alumno
    // =========================================
    @PostMapping("/delete")
    public mensajeDTO eliminar(@RequestHeader("Authorization") String header,
                               @RequestBody alumnoDTO a) {


        if (!validarToken(header)) {
            return new mensajeDTO("error", "token Invalido", null);
        }

        Alumno existente = alumnoService.buscarPorId(a.getIdAlumno());

        existente.setEstado(0);
        alumnoService.eliminar(existente);
        return new mensajeDTO("ok", "Alumno eliminado exitosamente", null);
    }
}
