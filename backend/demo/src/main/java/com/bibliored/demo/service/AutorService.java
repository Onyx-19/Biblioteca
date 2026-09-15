package com.bibliored.demo.service;

import com.bibliored.demo.dto.AutorRequestDTO;
import com.bibliored.demo.dto.AutorResponseDTO;

import java.util.List;

public interface AutorService {
    AutorResponseDTO crear(AutorRequestDTO request);

    List<AutorResponseDTO> listarTodos();

    AutorResponseDTO obtenerPorID(Long id);

    AutorResponseDTO actualizar(Long id, AutorRequestDTO request);

    void eliminar(Long id);
}
