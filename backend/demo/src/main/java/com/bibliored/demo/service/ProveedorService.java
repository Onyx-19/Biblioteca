package com.bibliored.demo.service;

import com.bibliored.demo.dto.ProveedorRequestDTO;
import com.bibliored.demo.dto.ProveedorResponseDTO;

import java.util.List;

public interface ProveedorService {
    ProveedorResponseDTO crear(ProveedorRequestDTO request);

    List<ProveedorResponseDTO> listarTodos();

    ProveedorResponseDTO obtenerPorId(Long id);

    ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO request);

    void eliminar(Long id);

}
