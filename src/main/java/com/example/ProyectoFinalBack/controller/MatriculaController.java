package com.example.ProyectoFinalBack.controller;

import com.example.ProyectoFinalBack.dto.infoMatriculaDTO;
import com.example.ProyectoFinalBack.dto.matriculaDTO;
import com.example.ProyectoFinalBack.dto.mensajeDTO;
import com.example.ProyectoFinalBack.model.Alumno;
import com.example.ProyectoFinalBack.model.Grado;
import com.example.ProyectoFinalBack.model.Matricula;
import com.example.ProyectoFinalBack.service.AlumnoService;
import com.example.ProyectoFinalBack.service.CuotaService;
import com.example.ProyectoFinalBack.service.GradoService;
import com.example.ProyectoFinalBack.service.MatriculaService;
import com.example.ProyectoFinalBack.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/matriculas")
@CrossOrigin(origins = "*")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;
    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private GradoService gradoService;
    @Autowired
    private CuotaService cuotaService;
    @Autowired
    private JwtUtil jwtUtil;

    // =========================================
    // 🔒 Validación de Token SEGURA
    // =========================================
    private void validarTokenOThrow(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Acceso denegado: Token no enviado.");
        }
        String token = header.substring(7);
        String usuario = jwtUtil.obtenerUsuarioDelToken(token);
        if (!jwtUtil.validarToken(token, usuario)) {
            throw new RuntimeException("Acceso denegado: Token inválido o expirado.");
        }
    }

    private boolean validarTokenBoolean(String header) {
        try {
            validarTokenOThrow(header);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    @GetMapping("/listar")
    public List<infoMatriculaDTO> listarTodas(@RequestHeader("Authorization") String header) {
        validarTokenOThrow(header);
        List<Matricula> listaEntidades = matriculaService.listar();
        return listaEntidades.stream()
                .map(infoMatriculaDTO::new)
                .collect(Collectors.toList());
    }

    // =========================================
    // 🔍 MÉTODOS DE BÚSQUEDA
    // =========================================

    @GetMapping("/buscar/dni/{dni}")
    public List<infoMatriculaDTO> buscarPorDni(@RequestHeader("Authorization") String header,
                                               @PathVariable String dni) {
        validarTokenOThrow(header);
        List<Matricula> listaEntidades = matriculaService.buscarPorDni(dni);
        return listaEntidades.stream()
                .map(infoMatriculaDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/buscar/anio/{anio}")
    public List<infoMatriculaDTO> buscarPorAnio(@RequestHeader("Authorization") String header,
                                                @PathVariable String anio) {
        validarTokenOThrow(header);
        List<Matricula> listaEntidades = matriculaService.listarPorAnio(anio);
        return listaEntidades.stream()
                .map(infoMatriculaDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/buscar/grado/{idGrado}")
    public List<infoMatriculaDTO> buscarPorGrado(@RequestHeader("Authorization") String header,
                                                 @PathVariable Integer idGrado) {
        validarTokenOThrow(header);
        List<Matricula> listaEntidades = matriculaService.listarPorGrado(idGrado);
        return listaEntidades.stream()
                .map(infoMatriculaDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/filtrar")
    public List<infoMatriculaDTO> filtrar(@RequestHeader("Authorization") String header,
                                          @RequestParam(required = false) String anio,
                                          @RequestParam(required = false) Integer grado) {
        validarTokenOThrow(header);
        List<Matricula> resultadoEntidades;

        if (anio != null && grado != null) {
            resultadoEntidades = matriculaService.filtrarPorAnioYGrado(anio, grado);
        } else if (anio != null) {
            resultadoEntidades = matriculaService.listarPorAnio(anio);
        } else if (grado != null) {
            resultadoEntidades = matriculaService.listarPorGrado(grado);
        } else {
            return new ArrayList<>();
        }

        return resultadoEntidades.stream()
                .map(infoMatriculaDTO::new)
                .collect(Collectors.toList());
    }

    // =========================================
    // 📝 Registrar Matrícula
    // =========================================
    @PostMapping("/registrar")
    public mensajeDTO registrar(@RequestHeader("Authorization") String header,
                                @RequestBody matriculaDTO dto) {

        if (!validarTokenBoolean(header)) {
            return new mensajeDTO("error", "Token inválido o expirado", null);
        }

        if (dto.getIdAlumno() == null || dto.getIdGrado() == null || dto.getAnioAcademico() == null) {
            return new mensajeDTO("error", "Faltan datos requeridos", null);
        }

        Integer idAlumnoInt;
        try {
            idAlumnoInt = Integer.parseInt(dto.getIdAlumno());
        } catch (NumberFormatException e) {
            return new mensajeDTO("error", "El ID del alumno no es válido", null);
        }

        Alumno alumnoEncontrado = alumnoService.buscarPorId(idAlumnoInt);
        if (alumnoEncontrado == null) return new mensajeDTO("error", "Alumno no encontrado", null);
        if (alumnoEncontrado.getEstado() == 0) return new mensajeDTO("error", "Alumno inactivo", null);

        Grado gradoEncontrado = gradoService.buscarPorId(dto.getIdGrado());
        if (gradoEncontrado == null) return new mensajeDTO("error", "Grado no encontrado", null);

        if (matriculaService.existeMatricula(alumnoEncontrado.getIdAlumno(), dto.getAnioAcademico())) {
            return new mensajeDTO("error", "El alumno ya está matriculado en " + dto.getAnioAcademico(), null);
        }

        Matricula nuevaMatricula = new Matricula();
        nuevaMatricula.setAlumno(alumnoEncontrado);
        nuevaMatricula.setGrado(gradoEncontrado);
        nuevaMatricula.setAnioAcademico(dto.getAnioAcademico());
        nuevaMatricula.setObservacion(dto.getObservacion());

        Matricula matriculaGuardada = matriculaService.registrar(nuevaMatricula);

        // ============================================
        // Generar Cuotas automáticamente (Lógica movida al Service)
        // ============================================
        try {
            cuotaService.generarCuotasParaMatricula(matriculaGuardada);
        } catch (Exception e) {
            e.printStackTrace();
            return new mensajeDTO("error", "Matrícula creada pero error al generar cuotas: " + e.getMessage(), null);
        }

        return new mensajeDTO("ok", "Matrícula registrada correctamente", null);
    }
}
