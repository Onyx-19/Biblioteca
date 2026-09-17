package com.bibliored.demo.dto;

import jakarta.persistence.Column;

public record CategoriaResponseDTO(
        Long idSucursales,
        String nombreCategoria,
        String codigoEstante
) {
}
