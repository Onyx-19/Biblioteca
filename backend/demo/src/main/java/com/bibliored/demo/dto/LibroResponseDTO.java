package com.bibliored.demo.dto;

public record LibroResponseDTO(
        Long idLibro,
        String tituloLibro,
        String editorial,
        String isbn,
        Integer annoPublicacion,
        Integer stock,
        Integer valorLibro,
        Long idAutor,
        String nombreAutor,
        Long idCategoria,
        String nombreCategoria,
        Long idSucursal,
        String nombreSucursal
) {
}
