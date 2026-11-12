package com.bibliored.demo.dto;

import com.bibliored.demo.enums.EstadoPrestamo;
import com.bibliored.demo.enums.MotivoMovimiento;
import com.bibliored.demo.enums.MovimientoInventario;

import java.time.LocalDate;
import java.util.List;

public record PrestamoResponseDTO (
        Long idTransaccion,
        MovimientoInventario movimientoInventario,
        MotivoMovimiento motivoMovimiento,
        LocalDate fechaRegistro,
        EstadoPrestamo estadoPrestamo,
        LocalDate fechaDevolucionEstimada,
        LocalDate fechaDevolucion,

        Long idSucursal,
        String nombreSucursal,
        Long idUsuario,
        String nombreUsuario,
        Long idCliente,
        String nombreCliente,
        String identificacionCliente,
        List<PrestamoDetalleResponseDTO> items
){
}
