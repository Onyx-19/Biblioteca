package com.bibliored.demo.service;

import com.bibliored.demo.dto.*;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO crear(UsuarioRequestDTO request);

    List<UsuarioResponseDTO> listarTodos();

    UsuarioResponseDTO obtenerPorId(Long id);

    UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO request);

    void eliminar(Long id);

    void cambiarPasswordInicial(Long id, UsuarioPassInicialRequestDTO request);

    UsuarioResponseDTO perfil();

    UsuarioResponseDTO actualizarPerfil(UsuarioPerfilRequestDTO request);

    void cambioContrasena(UsuarioCambioPassRequestDTO request);

}