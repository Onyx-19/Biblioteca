package com.bibliored.demo.dto;

public record UsuarioAuthResponseDTO(
        String token,
        Long idUsuario,
        String nombre,
        String rol,
        Boolean cambioPassInicial
) {
}
