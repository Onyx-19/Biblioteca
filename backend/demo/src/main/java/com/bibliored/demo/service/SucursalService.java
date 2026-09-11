package com.bibliored.demo.service;

import com.bibliored.demo.dto.SucursalRequestDTO;
import com.bibliored.demo.dto.SucursalResponseDTO;

import java.util.List;

public interface SucursaleService {

    SucursalResponseDTO crear (SucursalRequestDTO request);

    List<SucursalRequestDTO> listarTodas();

//    Test
//    SucursalResponseDTO obtenerPorNombre(String nombreSucursal);

    SucursalResponseDTO obtenerPorId(Integer id);

    SucursalResponseDTO actualizar(Integer id, SucursalRequestDTO request);

    void eliminar(Integer id);
}