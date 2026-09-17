package com.bibliored.demo.service;

import com.bibliored.demo.dto.ClienteRequestDTO;
import com.bibliored.demo.dto.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO crear(ClienteRequestDTO request);

    List<ClienteResponseDTO> listarTodos();

    ClienteResponseDTO obtenerPorId(Long id);

    ClienteResponseDTO actualizar(Long id, ClienteRequestDTO request);

    void eliminar(Long id);
}
