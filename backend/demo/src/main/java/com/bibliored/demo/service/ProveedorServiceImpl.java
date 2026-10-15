package com.bibliored.demo.service;

import com.bibliored.demo.component.ValidadorNegocio;
import com.bibliored.demo.dto.ProveedorRequestDTO;
import com.bibliored.demo.dto.ProveedorResponseDTO;
import com.bibliored.demo.model.Proveedor;
import com.bibliored.demo.repository.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService{

    private final ProveedorRepository proveedorRepository;
    private final ValidadorNegocio validadorNegocio;

    public ProveedorServiceImpl (ProveedorRepository proveedorRepository,
                                 ValidadorNegocio validadorNegocio){
        this.proveedorRepository = proveedorRepository;
        this.validadorNegocio = validadorNegocio;
    }

    @Override
    public ProveedorResponseDTO crear(ProveedorRequestDTO request) {
        Proveedor proveedor = new Proveedor();
        String identificacion = request.identificacionProveedor();
        String nombre = validadorNegocio.formatoNombre(request.nombreProveedor());
        String correo = validadorNegocio.formatoCorreo(request.correoProveedor());

        validadorNegocio.validarIdentificacionDuplicadoProveedor(identificacion, null);
        validadorNegocio.validarCorreoDuplicadoProveedor(correo, null);

        proveedor.setIdentificacionProveedor(identificacion);
        proveedor.setNombreProveedor(nombre);
        proveedor.setCorreoProveedor(correo);
        proveedor.setDireccionProveedor(request.direccionProveedor());
        proveedor.setTelefonoProveedor(request.telefonoProveedor());

        Proveedor guardado = proveedorRepository.save(proveedor);
        return toResponseDTO(guardado);
    }

    @Override
    public List<ProveedorResponseDTO> listarTodos() {
        return proveedorRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public ProveedorResponseDTO obtenerPorId(Long id) {
        Proveedor proveedor = validacionId(id);
        return toResponseDTO(proveedor);
    }

    @Override
    public ProveedorResponseDTO actualizar(Long id, ProveedorRequestDTO request) {
        Proveedor proveedor = validacionId(id);
        String identificacion = request.identificacionProveedor();
        String nombre = validadorNegocio.formatoNombre(request.nombreProveedor());
        String correo = validadorNegocio.formatoCorreo(request.correoProveedor());

        validadorNegocio.validarIdentificacionDuplicadoProveedor(identificacion, id);
        validadorNegocio.validarCorreoDuplicadoProveedor(correo, id);

        proveedor.setIdentificacionProveedor(identificacion);
        proveedor.setNombreProveedor(nombre);
        proveedor.setCorreoProveedor(correo);
        proveedor.setDireccionProveedor(request.direccionProveedor());
        proveedor.setTelefonoProveedor(request.telefonoProveedor());

        Proveedor actualizado = proveedorRepository.save(proveedor);
        return toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Proveedor proveedor = validacionId(id);
        proveedorRepository.delete(proveedor);
    }

    private Proveedor validacionId(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado proveedor con id " + id));
    }

    private ProveedorResponseDTO toResponseDTO(Proveedor proveedor) {
        return new ProveedorResponseDTO(
                proveedor.getIdProveedor(),
                proveedor.getIdentificacionProveedor(),
                proveedor.getNombreProveedor(),
                proveedor.getDireccionProveedor(),
                proveedor.getCorreoProveedor(),
                proveedor.getTelefonoProveedor()
        );

    }
}
