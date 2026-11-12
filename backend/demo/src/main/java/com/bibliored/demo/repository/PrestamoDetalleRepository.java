package com.bibliored.demo.repository;

import com.bibliored.demo.model.PrestamoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoDetalleRepository extends JpaRepository<PrestamoDetalle, Long> {
    List<PrestamoDetalle> findByIdTransaccion(Long id);
}
