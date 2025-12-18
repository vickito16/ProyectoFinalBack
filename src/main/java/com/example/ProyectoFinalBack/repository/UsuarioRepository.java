package com.example.ProyectoFinalBack.repository;


import com.example.ProyectoFinalBack.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // 🔹 Buscar usuario por login tradicional
    Optional<Usuario> findByLogiUsua(String logiUsua);

    // 🔹 Buscar usuario por proveedor e ID OAuth (por ejemplo: Google, GitHub)
    Optional<Usuario> findByOauthProviderAndOauthId(String oauthProvider, String oauthId);
}

