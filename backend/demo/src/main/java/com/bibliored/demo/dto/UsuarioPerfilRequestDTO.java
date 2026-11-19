package com.bibliored.demo.dto;

public record UsuarioPerfilRequestDTO(
        String nombreUsuario,
        String correoUsuario,
        String direccionUsuario,
        String telefonoUsuario
) {
}
