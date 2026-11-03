package com.bibliored.demo.dto;

public record InventarioDetalleResponseDTO(
        Long idDetalleInventario,
        Long idTransaccion,
        Long idLibro,
        String tituloLibro,
        Integer unidades
) {
}