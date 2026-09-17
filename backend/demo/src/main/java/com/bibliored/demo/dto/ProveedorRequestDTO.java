package com.bibliored.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProveedorRequestDTO(
        @NotBlank (message = "La identificacion del proveedor es obligatoria")
        @Size (max = 50)
        String identificacionProveedor,
        @NotBlank (message = "El nombre del proveedor es obligatorio")
        @Size (max = 100)
        String nombreProveedor,
        @Size (max = 100)
        String direccionProveedor,
        @NotBlank (message = "El correo del proveedor es obligatorio")
        @Email (message = "El correo no tiene un formato valido")
        @Size (max = 100)
        String correoProveedor,
        @Size (max = 15)
        String telefonoProveedor
) {
}
