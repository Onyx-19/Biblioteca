package com.bibliored.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank ( message = "El numero de identificacion del usuario es obligatorio")
        @Size(max = 50)
        String identificacionUsuario,
        @NotBlank (message = "El nombre de usuario es obligatorio")
        @Size(max = 100)
        String nombreUsuario,
        @NotBlank (message = "El correo del usuario es obligatorio")
        @Email (message = "El correo no tiene un formato valido")
        @Size(max = 100)
        String correoUsuario,
        @Size(max = 100)
        String direccionUsuario,
        @Size(max = 15)
        String telefonoUsuario,
        @NotBlank (message = "El rol del usuario es obligatorio")
        @Size(max = 50)
        String rolUsuario,
        @NotNull (message = "El usuario debe tener una sucursal relacionada")
        Long idSucursal
) {
}