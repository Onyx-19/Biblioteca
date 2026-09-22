package com.bibliored.demo.repository;

import com.bibliored.demo.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findById(Long aLong);
}
