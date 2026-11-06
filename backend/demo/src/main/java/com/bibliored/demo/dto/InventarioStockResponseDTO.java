package com.bibliored.demo.dto;

public record InventarioStockResponseDTO(
        Long idStock,
        Long idSucursal,
        String nombreSucursal,
        Long idLibro,
        String nombreLibro,
        Integer disponible
) {
}
