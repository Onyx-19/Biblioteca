package com.bibliored.demo.dto;

import com.bibliored.demo.enums.MotivoMovimiento;
import com.bibliored.demo.enums.MovimientoInventario;

import java.time.LocalDate;
import java.util.List;

public record InventarioResponseDTO(
        Long idTransaccion,
        MovimientoInventario movimientoInventario,
        MotivoMovimiento motivoMovimiento,
        LocalDate fechaHora,
        Long idSucursal,
        String nombreSucursal,
        Long idUsuario,
        String nombreUsuario,
        Long idProveedor,
        String nombreProveedor,
        List<InventarioDetalleResponseDTO> items
) {
}
