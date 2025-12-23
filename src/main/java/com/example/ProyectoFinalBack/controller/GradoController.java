package com.example.ProyectoFinalBack.controller;


import com.example.ProyectoFinalBack.model.Grado;
import com.example.ProyectoFinalBack.service.GradoService;
import com.example.ProyectoFinalBack.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grados")
@CrossOrigin(origins = "*")
public class GradoController {

    @Autowired
    private GradoService gradoService;

    @Autowired
    private JwtUtil jwtUtil;

    // =========================================
    // 🔹 Validar token (Método auxiliar)
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
    // 🔹 Listar Grados
    // =========================================
    @GetMapping("/listar")
    public ResponseEntity<?> listar(@RequestHeader("Authorization") String header) {
        if (!validarToken(header)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
        }
        return ResponseEntity.ok(gradoService.listar());
    }

}
