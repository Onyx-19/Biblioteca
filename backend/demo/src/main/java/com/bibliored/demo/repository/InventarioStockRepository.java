package com.bibliored.demo.repository;

import com.bibliored.demo.model.InventarioStock;
import com.bibliored.demo.model.Libro;
import com.bibliored.demo.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventarioStockRepository extends JpaRepository<InventarioStock, Long> {
    Optional<InventarioStock> findById (Long id);

    Optional<InventarioStock> findBySucursalAndLibro(Sucursal sucursal,
                                                         Libro libro);
}
