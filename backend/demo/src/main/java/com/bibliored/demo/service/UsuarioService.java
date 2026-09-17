package com.bibliored.demo.service;

import com.bibliored.demo.dto.UsuarioPassinicialDTO;
import com.bibliored.demo.dto.UsuarioRequestDTO;
import com.bibliored.demo.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO crear(UsuarioRequestDTO request);

    List<UsuarioResponseDTO> listarTodos();

    UsuarioResponseDTO obtenerPorId(Long id);

    UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO request);

    void eliminar(Long id);

    void cambiarPassword(Long id, UsuarioPassinicialDTO request);
}