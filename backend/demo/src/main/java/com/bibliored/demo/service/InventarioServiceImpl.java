package com.bibliored.demo.service;

import com.bibliored.demo.dto.InventarioRequestDTO;
import com.bibliored.demo.dto.InventarioResponseDTO;
import com.bibliored.demo.model.*;
import com.bibliored.demo.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService{
    private final InventarioRepository inventarioRepository;
    private final SucursalRepository sucursalRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProveedorRepository proveedorRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository,
                                 SucursalRepository sucursalRepository,
                                 UsuarioRepository usuarioRepository,
                                 ProveedorRepository proveedorRepository
    ) {
        this.inventarioRepository = inventarioRepository;
        this.sucursalRepository = sucursalRepository;
        this.usuarioRepository = usuarioRepository;
        this.proveedorRepository = proveedorRepository;
    }


    @Override
    public InventarioResponseDTO crear(InventarioRequestDTO request) {
        Inventario inventario = new Inventario();
        Sucursal sucursal = buscarSucursal(request.idSucursal());
        Usuario usuario = buscarUsuario(request.idUsuario());
        Proveedor proveedor = buscarProveedor(request.idProveedor());

        inventario.setMovimientoInventario(request.movimientoInventario());
        inventario.setMotivoMovimiento(request.motivoMovimiento());
        inventario.setFechaHora(LocalDate.now());
        inventario.setSucursal(sucursal);
        inventario.setUsuario(usuario);
        inventario.setProveedor(proveedor);

        Inventario guardado = inventarioRepository.save(inventario);
        return toResponseDTO(guardado);
    }

    @Override
    public List<InventarioResponseDTO> listarTodos() {
        return inventarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public InventarioResponseDTO obtenerPorId(Long id) {
        Inventario inventario = validacionPorId(id);
        return toResponseDTO(inventario);
    }

    @Override
    public InventarioResponseDTO actualizar(Long id, InventarioRequestDTO request) {
        Sucursal sucursal = buscarSucursal(request.idSucursal());
        Usuario usuario = buscarUsuario(request.idUsuario());
        Proveedor proveedor = buscarProveedor(request.idProveedor());
        Inventario inventario = validacionPorId(id);

        inventario.setMovimientoInventario(request.movimientoInventario());
        inventario.setMotivoMovimiento(request.motivoMovimiento());
        inventario.setFechaHora(request.fechaHora());
        inventario.setSucursal(sucursal);
        inventario.setUsuario(usuario);
        inventario.setProveedor(proveedor);

        Inventario actualizado = inventarioRepository.save(inventario);
        return toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Inventario inventario = validacionPorId(id);
        inventarioRepository.delete(inventario);
    }


    private Inventario validacionPorId(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado un registro de inventario con id: " + id));
    }

    private Sucursal buscarSucursal(Long id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado una sucursal con id: " + id));
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado una usuario con id: " + id));
    }

    private Proveedor buscarProveedor (Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado un proveedor con id: " + id));
    }


    private InventarioResponseDTO toResponseDTO(Inventario inventario) {
        return new InventarioResponseDTO(
                inventario.getIdTransaccion(),
                inventario.getMovimientoInventario(),
                inventario.getMotivoMovimiento(),
                inventario.getFechaHora(),
                inventario.getSucursal().getIdSucursal(),
                inventario.getSucursal().getNombreSucursal(),
                inventario.getUsuario().getIdUsuario(),
                inventario.getUsuario().getNombreUsuario(),
                inventario.getProveedor().getIdProveedor(),
                inventario.getProveedor().getNombreProveedor()
        );

    }
}
