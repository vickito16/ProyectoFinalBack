package com.example.ProyectoFinalBack.controller;


import com.example.ProyectoFinalBack.dto.infoCuotaDTO;
import com.example.ProyectoFinalBack.dto.mensajeDTO;
import com.example.ProyectoFinalBack.model.Cuota;
import com.example.ProyectoFinalBack.model.Recibo;
import com.example.ProyectoFinalBack.service.CuotaService;
import com.example.ProyectoFinalBack.service.ReciboService;
import com.example.ProyectoFinalBack.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cuotas")
@CrossOrigin(origins = "*")
public class CuotaController {

    @Autowired
    private CuotaService cuotaService;

    @Autowired
    private ReciboService reciboService;

    @Autowired
    private JwtUtil jwtUtil;

    // Metodo auxiliar de validación de Token
    private boolean validarToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String usuario = jwtUtil.obtenerUsuarioDelToken(token);
            return jwtUtil.validarToken(token, usuario);
        }
        return false;
    }

    // =========================================
    // 🔹 Listar Cuotas por ID de Matrícula
    // =========================================
    @GetMapping("/listar/{idMatricula}")
    public List<infoCuotaDTO> listarPorMatricula(
            @RequestHeader("Authorization") String header,
            @PathVariable Integer idMatricula) {

        // 1. Validar Token
        if (!validarToken(header)) {
            throw new RuntimeException("Token inválido o expirado");
        }

        // 2. Obtener Entidades
        List<Cuota> listaEntidades = cuotaService.listarPorMatricula(idMatricula);

        // 3. Convertir a DTOs (Id, Precio, Estado, NombreConcepto)
        return listaEntidades.stream()
                .map(infoCuotaDTO::new)
                .collect(Collectors.toList());
    }

    // =========================================
    // 🔹 Pagar Cuota (Actualizar Estado)
    // =========================================
    @PostMapping("/pagar/{idCuota}")
    public mensajeDTO pagarCuota(
            @RequestHeader("Authorization") String header,
            @PathVariable Integer idCuota) {

        if (!validarToken(header)) {
            return new mensajeDTO("error", "Token inválido", null);
        }

        Cuota cuota = cuotaService.buscarPorId(idCuota);

        if (cuota == null) {
            return new mensajeDTO("error", "Cuota no encontrada", null);
        }

        if (cuota.getEstadoPago() != null && cuota.getEstadoPago() == 1) {
            return new mensajeDTO("error", "Esta cuota ya se encuentra pagada", null);
        }

        // ========================================================
        // 1. LÓGICA DE RECIBO AUTOMÁTICO
        // ========================================================
        // Generamos el recibo usando el precio de la cuota
        Recibo nuevoRecibo = reciboService.generarRecibo(cuota.getPrecio());

        // ========================================================
        // 2. ACTUALIZACIÓN DE LA CUOTA
        // ========================================================
        cuota.setEstadoPago(1); // Cambiar a Pagado
        cuota.setIdRecibo(nuevoRecibo.getIdRecibo()); // VINCULAR EL ID GENERADO

        cuotaService.guardar(cuota);

        // Opcional: Devolvemos el código del recibo en el mensaje para mostrarlo al usuario
        String codigoRecibo = nuevoRecibo.getCodigoCompleto(); // ej: 001-0000001

        return new mensajeDTO("ok", "Pago exitoso. Se generó el recibo Nro: " + codigoRecibo, null);
    }
}

