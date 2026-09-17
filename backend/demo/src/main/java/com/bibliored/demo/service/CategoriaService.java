package com.bibliored.demo.service;

import com.bibliored.demo.dto.CategoriaRequestDTO;
import com.bibliored.demo.dto.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService{

    CategoriaResponseDTO crear(CategoriaRequestDTO request);

    List<CategoriaResponseDTO> ListarTodas();

    CategoriaResponseDTO obtenerPorID(Long id);

    CategoriaResponseDTO actualizar (Long id, CategoriaRequestDTO request);

    void eliminar(Long id);
}
