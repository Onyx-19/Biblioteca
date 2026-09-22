package com.bibliored.demo.dto;

import com.bibliored.demo.enums.EstadoPrestamo;
import com.bibliored.demo.enums.TipoTransaccion;

import java.time.LocalDateTime;

public record PrestamoResponseDTO (
        Long idTransaccion,
        TipoTransaccion tipoTransaccion,
        Integer unidades,
        LocalDateTime fechaRegistro,
        EstadoPrestamo estadoPrestamo,
        LocalDateTime fechaDevolucionEstimada,
        LocalDateTime fechaDevolucion,

        Long idSucursal,
        String nombreSucursal,
        Long idUsuario,
        String nombreUsuario,
        Long idLibro,
        String tituloLibro,
        Long idCliente,
        String nombreCliente
){
}
