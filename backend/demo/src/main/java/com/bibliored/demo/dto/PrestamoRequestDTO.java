package com.bibliored.demo.dto;

import com.bibliored.demo.enums.EstadoPrestamo;
import com.bibliored.demo.enums.TipoTransaccion;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PrestamoRequestDTO(
        @NotNull(message = "El tipo de transaccion es obligatorio")
        TipoTransaccion tipoTransaccion,
        @NotNull(message = "Las unidades son obligatorias")
        Integer unidades,
        @NotNull(message = "La fecha de registro es obligatoria")
        LocalDateTime fechaRegistro,
        @NotNull(message = "El estado del prestamo es obligatorio")
        EstadoPrestamo estadoPrestamo,
        @NotNull(message = "La fecha de devolucion estimada es obligatoria")
        LocalDateTime fechaDevolucionEstimada,
        LocalDateTime fechaDevolucion,

        @NotNull(message = "La sucursal es obligatoria")
        Long idSucursal,
        @NotNull(message = "El usuario es obligatorio")
        Long idUsuario,
        @NotNull(message = "El libro es obligatorio")
        Long idLibro,
        @NotNull(message = "El cliente es obligatorio")
        Long idCliente
) {
}
