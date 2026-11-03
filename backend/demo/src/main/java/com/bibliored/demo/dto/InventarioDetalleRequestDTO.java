package com.bibliored.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventarioDetalleRequestDTO(
        Long idTransaccion,
        @NotNull (message = "Es necesario referenciar un libro.")
        Long idLibro,
        @NotNull (message = "Es necesario indicar las unidades del item.")
        @Min(value = 1, message = "Las unidades deben ser numeros enteros")
        Integer unidades
) {
}
