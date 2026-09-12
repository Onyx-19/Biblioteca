package com.bibliored.demo.repository;

import com.bibliored.demo.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal,Long> {

    Optional<Sucursal> findByCorreoSucursal(String correoSucursal);
    boolean existsByCorreoSucursal(String correoSucursal);

    Optional<Sucursal> findById(Long id);
//    Test
//    Optional<Sucursal> findByName(String nombreSucursal);
//    Optional<Sucursal> findByAddress(String direccionSucursal);
}
