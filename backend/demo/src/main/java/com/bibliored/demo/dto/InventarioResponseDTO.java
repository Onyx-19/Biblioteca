package com.bibliored.demo.dto;

import com.bibliored.demo.enums.TransaccionInventario;

import java.time.LocalDateTime;

public record InventarioResponseDTO(
        Long idTransaccion,
        TransaccionInventario transaccionInventario,
        Integer unidades,
        Integer valorLibro,
        LocalDateTime fechaHora,

        Long idSucursal,
        String nombreSucursal,
        Long idUsuario,
        String nombreUsuario,
        Long idLibro,
        String tituloLibro,
        Long idProveedor,
        String nombreProveedor
) {
}
