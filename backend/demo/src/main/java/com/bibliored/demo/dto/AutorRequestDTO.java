package com.bibliored.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutorRequestDTO(
        @NotBlank (message = "El nombre del autor es obligatorio")
        @Size(max = 100)
        String nombreAutor,
        @Size(max = 4)
        String annoNacimiento,
        @Size(max = 4)
        String annoDeceso
) {
}