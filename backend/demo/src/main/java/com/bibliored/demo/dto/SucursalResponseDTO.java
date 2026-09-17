package com.bibliored.demo.dto;

public record SucursalResponseDTO(
        Long idSucursal,
        String nombreSucursal,
        String direccionSucursal,
        String correoSucursal,
        String telefonoSucursal
) {
}
