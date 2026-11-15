package com.bibliored.demo.dto;

import com.bibliored.demo.enums.Roles;

public record UsuarioResponseDTO(
    Long idUsuario,
    String identificacionUsuario ,
    String nombreUsuario,
    String correoUsuario,
    String direccionUsuario,
    String telefonoUsuario,
    Roles rolUsuario,
    Long idSucursal,
    String nombreSucursal
) {
}
