package com.bibliored.demo.dto;

import com.bibliored.demo.enums.MotivoMovimiento;
import com.bibliored.demo.enums.MovimientoInventario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record InventarioRequestDTO(
        @NotNull(message = "El tipo de movimiento de inventario es obligatorio")
        MovimientoInventario movimientoInventario,
        @NotNull(message = "El motivo del movimiento de inventario es obligatorio")
        MotivoMovimiento motivoMovimiento,
        @NotNull (message = "La fecha del registro de inventario es obligatoria.")
        LocalDate fechaHora,
        @NotNull(message = "La sucursal es obligatoria")
        Long idSucursal,
        @NotNull(message = "El usuario es obligatorio")
        Long idUsuario,
        Long idProveedor,
        @NotEmpty(message = "Debe agregar al menos un item")
        @Valid
        List <InventarioDetalleRequestDTO> items
) {
}
