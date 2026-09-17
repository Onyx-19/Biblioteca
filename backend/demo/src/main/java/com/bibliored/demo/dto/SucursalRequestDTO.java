package com.bibliored.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SucursalRequestDTO(
        @NotBlank(message = "El nombre de la sucursal es obligatorio")
        @Size(max = 100)
        String nombreSucursal,
        @Size (max = 100)
        String direccionSucursal,
        @Email(message = "El correo no tiene un formato valido")
        @Size (max = 100)
        String correoSucursal,
        @Size (max = 15)
        String telefonoSucursal
) {
}
