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

    // Mapa: "Juan" -> "session-123xyz"
    private final Map<String, String> usuariosRegistrados = new ConcurrentHashMap<>();

    // 1. REGISTRAR USUARIO AL CONECTARSE
    @MessageMapping("/registrar")
    public void registrarUsuario(@Payload String username, SimpMessageHeaderAccessor headerAccessor) {
        usuariosRegistrados.put(username, headerAccessor.getSessionId());
        System.out.println("✅ WebSocket: Usuario registrado: " + username + " ID: " + headerAccessor.getSessionId());
    }

    // 2. ENVÍO PRIVADO (El "Hack")
    @MessageMapping("/private")
    @SendToUser("/topic/private") // El destinatario escuchará en /user/topic/private
    public WebSocketResponse greet_private(@Payload messageDTO mensaje, SimpMessageHeaderAccessor headerAccessor) {

        String targetUser = mensaje.getTargetUsername(); // Ej: "Soporte"
        String targetSessionId = usuariosRegistrados.get(targetUser);

        if (targetSessionId != null) {
            // AQUÍ OCURRE LA MAGIA: Cambiamos la sesión del header por la del destino
            headerAccessor.setSessionId(targetSessionId);
            return new WebSocketResponse(mensaje.getLoEnvia() + ": " + mensaje.getMensaje());
        } else {
            // Si el usuario no existe, podrías manejar un error, pero por ahora no retornamos nada o un error genérico
            System.out.println("❌ Usuario destino no encontrado: " + targetUser);
            return new WebSocketResponse("Error: El usuario " + targetUser + " no está conectado.");
        }
    }

    // 3. CHECK DE CONEXIÓN
    @MessageMapping("/connected")
    @SendToUser("/topic/connected")
    public String connected(SimpMessageHeaderAccessor headerAccessor) {
        return headerAccessor.getSessionId();
    }
}
