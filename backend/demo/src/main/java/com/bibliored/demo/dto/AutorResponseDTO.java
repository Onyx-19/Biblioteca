package com.bibliored.demo.dto;

public record AutorResponseDTO (
        Long idAutor,
        String nombreAutor,
        String annoNacimiento,
        String annoDeceso
) {
}
