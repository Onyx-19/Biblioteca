package com.bibliored.demo.dto;

public record ClienteResponseDTO(
        Long idCliente,
        String identificacionCliente,
        String nombreCliente,
        String direccionCliente,
        String correoCliente,
        String telefonoCliente
) {
}
