package com.bibliored.demo.repository;

import com.bibliored.demo.model.Stock;
import com.bibliored.demo.model.Libro;
import com.bibliored.demo.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findById (Long id);

    Optional<Stock> findBySucursalAndLibro(Sucursal sucursal,
                                           Libro libro);
}
