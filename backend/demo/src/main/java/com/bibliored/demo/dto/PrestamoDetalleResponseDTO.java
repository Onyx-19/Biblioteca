package com.bibliored.demo.dto;

public record PrestamoDetalleResponseDTO(
        Long idDetallePrestamo,
        Long idTransaccion,
        Long idLibro,
        String tituloLibro,
        Integer unidades
) {
}
