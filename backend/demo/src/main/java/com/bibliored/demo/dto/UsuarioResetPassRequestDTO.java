package com.bibliored.demo.dto;

public record UsuarioResetPassRequestDTO(
        String identificacionUsuario,
        String correoUsuario
) {
}
