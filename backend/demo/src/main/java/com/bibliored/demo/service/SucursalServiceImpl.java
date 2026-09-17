package com.bibliored.demo.service;

import com.bibliored.demo.dto.SucursalRequestDTO;
import com.bibliored.demo.dto.SucursalResponseDTO;
import com.bibliored.demo.model.Sucursal;
import com.bibliored.demo.repository.SucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalServiceImpl implements SucursalService{

    private SucursalRepository sucursalRepository;

    public SucursalServiceImpl(SucursalRepository sucursalRepository){
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    public SucursalResponseDTO crear(SucursalRequestDTO request) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombreSucursal(request.nombreSucursal());
        sucursal.setDireccionSucursal(request.direccionSucursal());
        sucursal.setCorreoSucursal(request.correoSucursal());
        sucursal.setTelefonoSucursal(request.telefonoSucursal());

        Sucursal guardada = sucursalRepository.save(sucursal);
        return toResponseDTO(guardada);
    }

    @Override
    public List<SucursalResponseDTO> listarTodas() {
        return sucursalRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public SucursalResponseDTO obtenerPorId(Long id) {
        Sucursal sucursal = validacionId(id);
        return toResponseDTO(sucursal);
    }

    @Override
    public SucursalResponseDTO actualizar(Long id, SucursalRequestDTO request) {
        Sucursal sucursal = validacionId(id);

        sucursal.setNombreSucursal(request.nombreSucursal());
        sucursal.setDireccionSucursal(request.direccionSucursal());
        sucursal.setCorreoSucursal(request.correoSucursal());
        sucursal.setTelefonoSucursal(request.telefonoSucursal());

        Sucursal actualizada = sucursalRepository.save(sucursal);
        return toResponseDTO(actualizada);
    }

    @Override
    public void eliminar(Long id) {
        Sucursal sucursal = validacionId(id);
        sucursalRepository.delete(sucursal);
    }

    private Sucursal validacionId(Long id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron sucursales con id: " + id));
    }

    private SucursalResponseDTO toResponseDTO(Sucursal sucursal){
        return new SucursalResponseDTO(
                sucursal.getIdSucursal(),
                sucursal.getNombreSucursal(),
                sucursal.getDireccionSucursal(),
                sucursal.getCorreoSucursal(),
                sucursal.getTelefonoSucursal()
        );
    }
}
