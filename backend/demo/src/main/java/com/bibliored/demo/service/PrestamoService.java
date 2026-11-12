package com.bibliored.demo.service;

import com.bibliored.demo.dto.PrestamoDetalleResponseDTO;
import com.bibliored.demo.dto.PrestamoRequestDTO;
import com.bibliored.demo.dto.PrestamoResponseDTO;

import java.util.List;

public interface PrestamoService {
    PrestamoResponseDTO crear(PrestamoRequestDTO request);

    List<PrestamoResponseDTO> listarTodos();

    PrestamoResponseDTO obtenerPorId(Long id);

    PrestamoResponseDTO cierrePrestamo(Long id);

    List<PrestamoDetalleResponseDTO> consultarPrestamoDetalle(Long id);
}
