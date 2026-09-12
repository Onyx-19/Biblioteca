package com.bibliored.demo.dto;

import com.bibliored.demo.model.Sucursal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank
        @Size(max = 50)
        String identificacionUsuario,
        @NotBlank
        @Size(max = 100)
        String nombreUsuario,
        @Email
        @NotBlank
        @Size(max = 100)
        String correoUsuario,
        @NotBlank
        @Size(max = 100)
        String direccionUsuario,
        @Size(max = 15)
        String telefonoUsuario,
        @NotBlank
        @Size(max = 50)
        String rolUsuario,
        @NotNull
        Long idSucursal
) {
}