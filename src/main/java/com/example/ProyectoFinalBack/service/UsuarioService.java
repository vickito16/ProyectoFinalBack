package com.example.ProyectoFinalBack.service;


import com.example.ProyectoFinalBack.model.Usuario;
import com.example.ProyectoFinalBack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 🔹 Registrar nuevo usuario tradicional
    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario); // save() actualiza si el ID ya existe
    }

    // 🔹 Buscar usuario por login (para autenticación tradicional)
    public Usuario buscarPorLogin(String logiUsua) {
        return usuarioRepository.findByLogiUsua(logiUsua).orElse(null);
    }


    // =========================================================
    // 🔸 NUEVOS MÉTODOS PARA OAUTH 2.0
    // =========================================================

    // Registrar o actualizar usuario proveniente de OAuth
    public Usuario registrarOAuthUsuario(String provider, String oauthId, String email,
                                         String accessToken, String refreshToken, LocalDateTime expiration) {
// Buscar si el usuario ya existe por su proveedor OAuth e ID
        Optional<Usuario> optionalUsuario = usuarioRepository.findByOauthProviderAndOauthId(provider, oauthId);
        Usuario usuario;

        if (optionalUsuario.isPresent()) {
            // Usuario ya existe → actualizar tokens
            usuario = optionalUsuario.get();
        } else {
            // Nuevo usuario OAuth → crear registro
            usuario = new Usuario();
            usuario.setLogiUsua(email);  // normalmente el email se usa como login
            usuario.setOauthProvider(provider);
            usuario.setOauthId(oauthId);
        }

        // Actualizar tokens y expiración siempre
        usuario.setAccessToken(accessToken);
        usuario.setRefreshToken(refreshToken);
        usuario.setTokenExpiration(expiration);

        // Guardar y devolver
        return usuarioRepository.save(usuario);
    }
}

