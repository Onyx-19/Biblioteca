package com.bibliored.demo.repository;

import com.bibliored.demo.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal,Long> {
    Optional<Sucursal> findById(Long id);
    Optional<Sucursal> findByNombreSucursal(String nombreSucursal);
    Optional<Sucursal> findByCorreoSucursal(String correoSucursal);
}
