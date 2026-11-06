package com.bibliored.demo.service;

import com.bibliored.demo.dto.InventarioDetalleResponseDTO;
import com.bibliored.demo.dto.InventarioRequestDTO;
import com.bibliored.demo.dto.InventarioResponseDTO;
import com.bibliored.demo.model.InventarioDetalle;
import com.bibliored.demo.model.InventarioStock;

import java.util.List;

public interface InventarioService {
    InventarioResponseDTO crear (InventarioRequestDTO request);

    List<InventarioResponseDTO> listarTodos();

    InventarioResponseDTO obtenerPorId(Long id);

    List<InventarioStock> consultarStock();

    List<InventarioDetalleResponseDTO> consultarInventarioDetalle(Long id);
}
