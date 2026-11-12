package com.bibliored.demo.dto;

public record PrestamoDetalleRequestDTO(
        Long idTransaccion,
        Long idLibro,
        Integer unidades
) {
}
