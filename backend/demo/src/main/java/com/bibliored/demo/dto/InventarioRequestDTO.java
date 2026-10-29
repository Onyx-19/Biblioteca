package com.bibliored.demo.dto;

import com.bibliored.demo.enums.MotivoMovimiento;
import com.bibliored.demo.enums.MovimientoInventario;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record InventarioRequestDTO(
        @NotNull(message = "El tipo de movimiento de inventario es obligatorio")
        MovimientoInventario movimientoInventario,
        @NotNull(message = "El motivo del movimiento de inventario es obligatorio")
        MotivoMovimiento motivoMovimiento,
//        @NotNull(message = "La cantidad de unidades es obligatoria")
//        Integer unidades,
//        @NotNull(message = "El libro es obligatorio")
//        Long idLibro,
        LocalDate fechaHora,
        @NotNull(message = "La sucursal es obligatoria")
        Long idSucursal,
        @NotNull(message = "El usuario es obligatorio")
        Long idUsuario,
        @NotNull(message = "El proveedor es obligatorio")
        Long idProveedor
) {
}
