package com.bibliored.demo.dto;

public record InventarioStockRequestDTO(
        Long sucursal,
        Long idLibro,
        Integer disponible
) {
}
