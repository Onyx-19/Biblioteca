package com.bibliored.demo.service;

import com.bibliored.demo.dto.SucursalRequestDTO;
import com.bibliored.demo.dto.SucursalResponseDTO;

import java.util.List;

public interface SucursalService {

    SucursalResponseDTO crear (SucursalRequestDTO request);

    List<SucursalResponseDTO> listarTodas();

//    Test
//    SucursalResponseDTO obtenerPorNombre(String nombreSucursal);

    SucursalResponseDTO obtenerPorId(Long id);

    SucursalResponseDTO actualizar(Long id, SucursalRequestDTO request);

    void eliminar(Long id);
}