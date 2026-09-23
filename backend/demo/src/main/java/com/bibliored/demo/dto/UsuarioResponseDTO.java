package com.bibliored.demo.dto;

public record UsuarioResponseDTO(
    Long idUsuario,
    String identificacionUsuario ,
    String nombreUsuario,
    String correoUsuario,
    String direccionUsuario,
    String telefonoUsuario,
    String rolUsuario,
    Long idSucursal,
    String nombreSucursal
) {
}
