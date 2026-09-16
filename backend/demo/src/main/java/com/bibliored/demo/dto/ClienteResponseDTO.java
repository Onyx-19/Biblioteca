package com.bibliored.demo.dto;

import jakarta.persistence.Column;

public record ClienteResponseDTO(
        Long idCliente,
        String identificacionCliente,
        String nombreCliente,
        String direccionCliente,
        String correoCliente,
        String telefonoCliente
) {
}
