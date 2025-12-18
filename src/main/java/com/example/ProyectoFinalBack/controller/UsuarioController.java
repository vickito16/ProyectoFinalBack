package com.example.ProyectoFinalBack.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.ProyectoFinalBack.dto.cambioClaveDTO;
import com.example.ProyectoFinalBack.dto.credencialesDTO;
import com.example.ProyectoFinalBack.dto.mensajeDTO;
import com.example.ProyectoFinalBack.model.Usuario;
import com.example.ProyectoFinalBack.service.UsuarioService;
import com.example.ProyectoFinalBack.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // =========================================================
    // 🔹 LOGIN TRADICIONAL
    // =========================================================
    @PostMapping("/validacion")
    public mensajeDTO validacion(@RequestBody credencialesDTO credencialesDTO) {

        JwtUtil jwtUtilToken = new JwtUtil();
        Usuario u = usuarioService.buscarPorLogin(credencialesDTO.getLogin());

        if (u != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(credencialesDTO.getPassword(), u.getPassUsua())) {
                // Generar Token
                String token = jwtUtilToken.generarToken(credencialesDTO.getLogin());

                // Retorno Estandarizado: LOGIN|ROL
                return new mensajeDTO("ok", u.getLogiUsua() + "|" + u.getRol(), token);
            } else {
                return new mensajeDTO("Error", "Contraseña incorrecta", null);
            }
        } else {
            return new mensajeDTO("Error", "Usuario no encontrado", null);
        }
    }

    // =========================================================
    // 🔸 LOGIN vía OAUTH 2.0 (GOOGLE)
    // =========================================================
    @PostMapping("/oauth")
    public mensajeDTO loginOAuth(@RequestBody Map<String, String> body) {

        try {
            JwtUtil jwtUtilToken = new JwtUtil();

            // 1️⃣ Extraer el token
            String jwtToken = body.get("token");
            if (jwtToken == null || jwtToken.isEmpty()) {
                return new mensajeDTO("error", "Token JWT no proporcionado.", null);
            }

            // 2️⃣ Decodificar el JWT
            DecodedJWT decodedJWT = JWT.decode(jwtToken);

            // 3️⃣ Extraer datos
            String email = decodedJWT.getClaim("email").asString();
            String oauthId = decodedJWT.getSubject();
            Date expirationDate = decodedJWT.getExpiresAt();
            String name = decodedJWT.getClaim("name").asString(); // No lo usamos para el retorno

            LocalDateTime expiration = expirationDate != null
                    ? expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                    : LocalDateTime.now().plusHours(1);

            // 4️⃣ Registrar o Actualizar en BD
            Usuario usuario = usuarioService.registrarOAuthUsuario(
                    "google",
                    oauthId,
                    email,
                    jwtToken,
                    null,
                    expiration
            );

            // 5️⃣ Generar Token propio del sistema
            String token = jwtUtilToken.generarToken(usuario.getLogiUsua());

            // 6️⃣ Retorno Estandarizado: LOGIN|ROL (Usando datos de TU base de datos)
            return new mensajeDTO("ok", name + "|" + usuario.getRol(), token);

        } catch (JWTDecodeException e) {
            return new mensajeDTO("error", "Error al decodificar el JWT", null);
        } catch (Exception e) {
            return new mensajeDTO("error", "Error interno: " + e.getMessage(), null);
        }
    }


    @PostMapping("/registrar")
    public mensajeDTO registrar(@RequestBody credencialesDTO credencialesDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(credencialesDTO.getPassword());

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setLogiUsua(credencialesDTO.getLogin());
        nuevoUsuario.setPassUsua(hash);
        // El rol se pone solo en 'SECRETARIA' gracias a tu @PrePersist en la entidad
        usuarioService.registrarUsuario(nuevoUsuario);

        return new mensajeDTO("ok", "Registrado -> "+ nuevoUsuario.getLogiUsua(), null);
    }

    @PostMapping("/cambiarClave")
    public mensajeDTO cambiarClave(@RequestBody cambioClaveDTO dto) {
        Usuario usuario = usuarioService.buscarPorLogin(dto.getLogin());
        if (usuario == null) {
            return new mensajeDTO("error", "Usuario no encontrado", null);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(dto.getClaveActual(), usuario.getPassUsua())) {
            return new mensajeDTO("error", "La contraseña actual es incorrecta", null);
        }

        if (!dto.getNuevaClave().equals(dto.getConfirmaClave())) {
            return new mensajeDTO("error", "Las contraseñas nuevas no coinciden", null);
        }

        String nuevoHash = encoder.encode(dto.getNuevaClave());
        usuario.setPassUsua(nuevoHash);
        usuarioService.actualizarUsuario(usuario);

        return new mensajeDTO("ok", "Contraseña actualizada", null);
    }
}

