package com.bibliored.demo.service;

import com.bibliored.demo.dto.LibroRequestDTO;
import com.bibliored.demo.dto.LibroResponseDTO;

import java.util.List;

public interface LibroService {
    LibroResponseDTO crear(LibroRequestDTO request);

    List<LibroResponseDTO> listarTodos();

    LibroResponseDTO obtenerPorId(Long id);

    LibroResponseDTO actualizar(Long id, LibroRequestDTO request);

    void eliminar(Long id);
}
