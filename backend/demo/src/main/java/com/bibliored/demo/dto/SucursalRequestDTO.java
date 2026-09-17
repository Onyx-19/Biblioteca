package com.bibliored.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SucursalRequestDTO(
        @NotBlank(message = "El nombre de la sucursal es obligatorio")
        @Size(max = 100)
        String nombreSucursal,
        @NotBlank(message = "La direccion de la sucursal es obligatoria")
        @Size (max = 100)
        String direccionSucursal,
        @NotBlank (message = "El correo de la sucursal es obligatorio")
        @Email (message = "El correo no tiene un formato valido")
        @Size (max = 100)
        String correoSucursal,
        @NotBlank(message = "El telefono de la sucursal es obligatorio")
        @Size (max = 15)
        String telefonoSucursal
) {
}
