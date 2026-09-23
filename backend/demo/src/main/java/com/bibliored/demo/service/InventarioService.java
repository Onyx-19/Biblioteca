package com.bibliored.demo.service;

import com.bibliored.demo.dto.InventarioRequestDTO;
import com.bibliored.demo.dto.InventarioResponseDTO;

import java.util.List;

public interface InventarioService {
    InventarioResponseDTO crear (InventarioRequestDTO request);

    List<InventarioResponseDTO> listarTodos();

    InventarioResponseDTO obtenerPorId(Long id);

    InventarioResponseDTO actualizar(Long id, InventarioRequestDTO request);

    void eliminar(Long id);
}
