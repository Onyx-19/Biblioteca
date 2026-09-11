package com.blibliored.demo.repository;

import com.blibliored.demo.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal,Long> {
    Optional<Sucursal> findByNombre(String nombreSucursal);

    Optional<Sucursal> findByDireccion(String direccionSucursal);
}
