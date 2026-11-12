package com.bibliored.demo.dto;

public record StockRequestDTO(
        Long sucursal,
        Long idLibro,
        Integer disponible
) {
}
