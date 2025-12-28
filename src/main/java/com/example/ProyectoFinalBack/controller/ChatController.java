package com.example.ProyectoFinalBack.controller;

import com.example.ProyectoFinalBack.dto.messageDTO;
import com.example.ProyectoFinalBack.model.WebSocketResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ChatController {

    // Mapa para guardar la relación: "nombreUsuario" -> "sessionId"
    private final Map<String, String> usuariosRegistrados = new ConcurrentHashMap<>();

    // 1. REGISTRAR USUARIO AL CONECTARSE
    @MessageMapping("/registrar")
    public void registrarUsuario(@Payload String username, SimpMessageHeaderAccessor headerAccessor) {
        // Limpiamos espacios en blanco para evitar errores de tipeo
        String usuarioLimpio = username.trim();

        usuariosRegistrados.put(usuarioLimpio, headerAccessor.getSessionId());

        System.out.println("✅ WebSocket: Usuario registrado: '" + usuarioLimpio + "' ID: " + headerAccessor.getSessionId());
        System.out.println("📋 Lista actual de usuarios: " + usuariosRegistrados.keySet());
    }

    // 2. ENVÍO PRIVADO
    @MessageMapping("/private")
    @SendToUser("/topic/private")
    public WebSocketResponse greet_private(@Payload messageDTO mensaje, SimpMessageHeaderAccessor headerAccessor) {

        // Obtenemos el destinatario y limpiamos espacios
        String targetUser = mensaje.getTargetUsername().trim();

        // Buscamos su ID de sesión
        String targetSessionId = usuariosRegistrados.get(targetUser);

        if (targetSessionId != null) {
            // "Hack": Cambiamos la sesión del header para que Spring envíe el mensaje a ESA sesión
            headerAccessor.setSessionId(targetSessionId);

            // Retornamos el objeto con el formato correcto
            return new WebSocketResponse(mensaje.getLoEnvia() + ": " + mensaje.getMensaje());
        } else {
            // LOG DE ERROR PARA DEPURAR
            System.out.println("❌ Error: No se encontró al usuario destino: '" + targetUser + "'");
            System.out.println("🔍 Usuarios disponibles en memoria: " + usuariosRegistrados.keySet());

            return new WebSocketResponse("Error: El usuario " + targetUser + " no está conectado.");
        }
    }

    // 3. CHECK DE CONEXIÓN (Opcional, pero útil)
    @MessageMapping("/connected")
    @SendToUser("/topic/connected")
    public String connected(SimpMessageHeaderAccessor headerAccessor) {
        return headerAccessor.getSessionId();
    }
}