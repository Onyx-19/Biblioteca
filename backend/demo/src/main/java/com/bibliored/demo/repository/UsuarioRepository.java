package com.bibliored.demo.repository;

import com.bibliored.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByIdentificacionUsuario(String identificacionUsuario);
    boolean existsByIdentificacionUsuario(String identificacionUsuario);
}
