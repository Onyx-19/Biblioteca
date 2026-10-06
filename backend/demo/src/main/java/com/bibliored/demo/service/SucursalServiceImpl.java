package com.bibliored.demo.service;

import com.bibliored.demo.component.ValidadorNegocio;
import com.bibliored.demo.dto.SucursalRequestDTO;
import com.bibliored.demo.dto.SucursalResponseDTO;
import com.bibliored.demo.exceptions.ResourceNotFoundException;
import com.bibliored.demo.model.Sucursal;
import com.bibliored.demo.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalServiceImpl implements SucursalService{

    private final SucursalRepository sucursalRepository;
    private final ValidadorNegocio validadorNegocio;

    public SucursalServiceImpl(SucursalRepository sucursalRepository, ValidadorNegocio validadorNegocio){
        this.sucursalRepository = sucursalRepository;
        this.validadorNegocio = validadorNegocio;
    }

    @Override
    public SucursalResponseDTO crear(SucursalRequestDTO request) {
        Sucursal sucursal = new Sucursal();
        String nombre = validadorNegocio.formatoNombre(request.nombreSucursal());
        String correo = validadorNegocio.formatoCorreo(request.correoSucursal());

        validadorNegocio.validarNombreDuplicado(nombre, null, "Sucursal");
        validadorNegocio.validarCorreoDuplicado(correo, null, "Sucursal");

        sucursal.setNombreSucursal(nombre);
        sucursal.setDireccionSucursal(request.direccionSucursal());
        sucursal.setCorreoSucursal(correo);
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
        String nombre = validadorNegocio.formatoNombre(request.nombreSucursal());
        String correo = validadorNegocio.formatoCorreo(request.correoSucursal());

        validadorNegocio.validarNombreDuplicado(nombre, id, "Sucursal");
        validadorNegocio.validarCorreoDuplicado(correo, id, "Sucursal");

        sucursal.setNombreSucursal(nombre);
        sucursal.setDireccionSucursal(request.direccionSucursal());
        sucursal.setCorreoSucursal(correo);
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
                .orElseThrow(() -> new ResourceNotFoundException("No se encontraron sucursales con id: " + id));
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
