package com.bibliored.demo.repository;

import com.bibliored.demo.enums.EstadoPrestamo;
import com.bibliored.demo.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    Optional<Prestamo> findById(Long id);

    @Modifying
    @Query("UPDATE Prestamo p SET p.estadoPrestamo = :vencido " +
            "WHERE p.estadoPrestamo = :activo AND p.fechaDevolucionEstimada < :hoy")
    int marcarVencidos(@Param("activo") EstadoPrestamo activo,
                       @Param("vencido") EstadoPrestamo vencido,
                       @Param("hoy") LocalDate hoy);
}
