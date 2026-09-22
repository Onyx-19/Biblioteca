package com.bibliored.demo.dto;

import com.bibliored.demo.enums.MovimientoInventario;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record InventarioRequestDTO(
        @NotNull(message = "El tipo de movimiento de inventario es obligatorio")
        MovimientoInventario movimientoInventario,
        @NotNull(message = "La cantidad de unidades es obligatoria")
        Integer unidades,
        LocalDateTime fechaHora,
        @NotNull(message = "La sucursal es obligatoria")
        Long idSucursal,
        @NotNull(message = "El usuario es obligatorio")
        Long idUsuario,
        @NotNull(message = "El libro es obligatorio")
        Long idLibro,
        @NotNull(message = "El proveedor es obligatorio")
        Long idProveedor
) {
}
