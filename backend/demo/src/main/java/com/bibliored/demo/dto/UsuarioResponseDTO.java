package com.bibliored.demo.dto;

import com.bibliored.demo.model.Sucursal;

public record UsuarioResponseDTO(
    Long idUsuario,
    String identificacionUsuario ,
    String nombreUsuario,
    String correoUsuario,
    String direccionUsuario,
    String telefonoUsuario,
    String rolUsuario,
    Long idSucursal
) {
}
