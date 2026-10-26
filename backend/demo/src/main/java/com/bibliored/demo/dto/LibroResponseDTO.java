package com.bibliored.demo.dto;

public record LibroResponseDTO(
        Long idLibro,
        String tituloLibro,
        String editorial,
        String isbn,
        Integer annoPublicacion,
        Integer valorLibro,
        Long idAutor,
        String nombreAutor,
        Long idCategoria,
        String nombreCategoria
) {
}
