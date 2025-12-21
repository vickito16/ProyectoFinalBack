package com.example.ProyectoFinalBack.controller;



import com.example.ProyectoFinalBack.dto.anulacionDTO;
import com.example.ProyectoFinalBack.dto.mensajeDTO;
import com.example.ProyectoFinalBack.service.ReciboService;
import com.example.ProyectoFinalBack.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recibos")
@CrossOrigin(origins = "*")
public class ReciboController {

    @Autowired
    private ReciboService reciboService;

    @Autowired
    private JwtUtil jwtUtil;

    // Validación de Token (Reutilizamos tu lógica)
    private boolean validarToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String usuario = jwtUtil.obtenerUsuarioDelToken(token);
            return jwtUtil.validarToken(token, usuario);
        }
        return false;
    }

    @PostMapping("/anular")
    public mensajeDTO anularRecibo(@RequestHeader("Authorization") String header,
                                   @RequestBody anulacionDTO dto) {

        // 1. Validar que quien hace la petición (Secretaria) esté logueada
        if (!validarToken(header)) {
            return new mensajeDTO("error", "Token inválido o expirado", null);
        }

        // 2. Validar datos de entrada
        if (dto.getIdRecibo() == null || dto.getCodigoDirector() == null) {
            return new mensajeDTO("error", "Faltan datos (ID Recibo o Código 2FA)", null);
        }

        // 3. Ejecutar Anulación
        try {
            reciboService.anularRecibo(dto.getIdRecibo(), dto.getCodigoDirector());
            return new mensajeDTO("ok", "Recibo anulado exitosamente. Las cuotas han vuelto a estado 'Debe'.", null);

        } catch (RuntimeException e) {
            return new mensajeDTO("error", e.getMessage(), null);
        }
    }
}
