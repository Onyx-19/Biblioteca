package com.bibliored.demo.repository;

import com.bibliored.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository <Cliente, Long> {
    Optional<Cliente> findById(Long id);
    Optional<Cliente> findByIdentificacionCliente(String identificacionCliente);
    Optional<Cliente> findByCorreoCliente(String correo);
}
