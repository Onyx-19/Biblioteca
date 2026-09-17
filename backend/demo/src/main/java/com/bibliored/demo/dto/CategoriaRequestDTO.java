package com.bibliored.demo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(
        @NotBlank (message = "El nombre de la categoria es un dato obligatorio")
        @Size (max = 100)
        String nombreCategoria,
        @Size (max = 20)
        String codigoEstante
) {
}
