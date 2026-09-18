package com.bibliored.demo.dto;

public record LibroResponseDTO(
        Long idLibro,
        String tituloLibro,
        String editorial,
        String isbn,
        Integer annoPublicacion,
        Long idAutor,
        String nombreAutor,
        Long idCategoria,
        String nombreCategoria
) {
}
