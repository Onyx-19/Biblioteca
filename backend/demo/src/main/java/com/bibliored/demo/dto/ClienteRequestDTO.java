package com.bibliored.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
        @NotBlank (message = "El numero de identificacion del cliente es obligatorio")
        @Size (max = 50)
        String identificacionCliente,
        @NotBlank (message = "El nombre del cliente es obligatorio")
        @Size (max = 200)
        String nombreCliente,
        @Size (max = 100)
        String direccionCliente,
        @NotBlank (message = "El correo del cliente es obligatorio")
        @Email (message = "El correo no tiene un formato valido")
        @Size (max = 100)
        String correoCliente,
        @NotBlank (message = "El telefono de contacto del cliente es obligatorio")
        @Size (max = 15)
        String telefonoCliente
) {
}
