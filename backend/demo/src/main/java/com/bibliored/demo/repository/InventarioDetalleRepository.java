package com.bibliored.demo.repository;

import com.bibliored.demo.model.InventarioDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventarioDetalleRepository extends JpaRepository<InventarioDetalle, Long> {
    Optional<InventarioDetalle> findById(Long id);
}
