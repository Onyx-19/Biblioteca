package com.bibliored.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioPassInicialRequestDTO(
        @NotBlank (message = "La contraseña es un dato obligatorio")
        String contrasena
) {
}
