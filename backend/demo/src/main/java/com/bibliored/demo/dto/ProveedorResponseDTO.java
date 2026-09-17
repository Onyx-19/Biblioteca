package com.bibliored.demo.dto;

public record ProveedorResponseDTO(
        Long idProveedor,
        String identificacionProveedor,
        String nombreProveedor,
        String direccionProveedor,
        String correoProveedor,
        String telefonoProveedor
) {
}
