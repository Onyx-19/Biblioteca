package com.bibliored.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PrestamoRequestDTO(
        @NotNull(message = "La sucursal es obligatoria")
        Long idSucursal,
        @NotNull(message = "El usuario es obligatorio")
        Long idUsuario,
        @NotNull(message = "El cliente es obligatorio")
        Long idCliente,
        @NotEmpty(message = "Debe agregar al menos un item")
        @Valid
        List <PrestamoDetalleRequestDTO> items
) {
}
