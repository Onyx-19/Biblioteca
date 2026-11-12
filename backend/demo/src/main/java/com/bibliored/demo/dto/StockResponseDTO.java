package com.bibliored.demo.dto;

public record StockResponseDTO(
        Long idStock,
        Long idSucursal,
        String nombreSucursal,
        Long idLibro,
        String nombreLibro,
        Integer disponible
) {
}
