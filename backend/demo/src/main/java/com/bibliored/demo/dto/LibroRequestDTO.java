package com.bibliored.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LibroRequestDTO(
        @NotBlank (message = "El titulo del libro es obligatorio")
        @Size (max = 100)
        String tituloLibro,
        @Size (max = 100)
        String editorial,
        @Size (max = 15)
        String isbn,
        Integer annoPublicacion,
        @NotNull (message = "El stock es obligatorio")
        Integer stock,
        @NotNull (message = "El autor del libro es obligatorio")
        Long idAutor,
        @NotNull (message = "La categoria del libro es obligatoria")
        Long idCategoria,
        @NotNull (message = "La sucursal es obligatoria")
        Long idSucursal
) {
}
