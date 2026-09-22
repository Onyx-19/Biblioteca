package com.bibliored.demo.repository;

import com.bibliored.demo.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    Optional<Prestamo> findById(Long id);
}
