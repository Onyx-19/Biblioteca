package com.bibliored.demo.repository;

import com.bibliored.demo.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository <Proveedor, Long> {
    Optional<Proveedor> findById(Long id);
    Optional<Proveedor> findByNombreProveedor(String nombreProveedor);
    Optional<Proveedor> findByIdentificacionProveedor(String identificacionProveedor);
}
