package com.bibliored.demo.dto;

import com.bibliored.demo.enums.TransaccionInventario;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record InventarioRequestDTO(
        @NotNull(message = "El tipo de transaccion es obligatorio")
        TransaccionInventario transaccionInventario,
        @NotNull(message = "La cantidad de unidades es obligatoria")
        Integer unidades,
        @NotNull(message = "El valor del libro es obligatorio")
        Integer valorLibro,
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
