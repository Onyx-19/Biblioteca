package com.bibliored.demo.service;

import com.bibliored.demo.dto.InventarioRequestDTO;
import com.bibliored.demo.dto.InventarioResponseDTO;
import com.bibliored.demo.model.*;
import com.bibliored.demo.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService{
    private InventarioRepository inventarioRepository;
    private SucursalRepository sucursalRepository;
    private UsuarioRepository usuarioRepository;
    private LibroRepository libroRepository;
    private ProveedorRepository proveedorRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository,
                                 SucursalRepository sucursalRepository,
                                 UsuarioRepository usuarioRepository,
                                 LibroRepository libroRepository,
                                 ProveedorRepository proveedorRepository
    ) {
        this.inventarioRepository = inventarioRepository;
        this.sucursalRepository = sucursalRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.proveedorRepository = proveedorRepository;
    }


    @Override
    public InventarioResponseDTO crear(InventarioRequestDTO request) {
        Inventario inventario = new Inventario();
        Sucursal sucursal = buscarSucursal(request.idSucursal());
        Usuario usuario = buscarUsuario(request.idUsuario());
        Libro libro = buscarLibro(request.idLibro());
        Proveedor proveedor = buscarProveedor(request.idProveedor());

        inventario.setMovimientoInventario(request.movimientoInventario());
        inventario.setUnidades(request.unidades());
        inventario.setFechaHora(LocalDateTime.now());
        inventario.setSucursal(sucursal);
        inventario.setUsuario(usuario);
        inventario.setLibro(libro);
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
        Libro libro = buscarLibro(request.idLibro());
        Proveedor proveedor = buscarProveedor(request.idProveedor());
        Inventario inventario = validacionPorId(id);

        inventario.setMovimientoInventario(request.movimientoInventario());
        inventario.setUnidades(request.unidades());
        inventario.setFechaHora(LocalDateTime.now());
        inventario.setSucursal(sucursal);
        inventario.setUsuario(usuario);
        inventario.setLibro(libro);
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

    private Libro buscarLibro (Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado un libro con id: " + id));
    }

    private Proveedor buscarProveedor (Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado un proveedor con id: " + id));
    }


    private InventarioResponseDTO toResponseDTO(Inventario inventario) {
        return new InventarioResponseDTO(
                inventario.getIdTransaccion(),
                inventario.getMovimientoInventario(),
                inventario.getUnidades(),
                inventario.getFechaHora(),
                inventario.getSucursal().getIdSucursal(),
                inventario.getSucursal().getNombreSucursal(),
                inventario.getUsuario().getIdUsuario(),
                inventario.getUsuario().getNombreUsuario(),
                inventario.getLibro().getIdLibro(),
                inventario.getLibro().getTituloLibro(),
                inventario.getProveedor().getIdProveedor(),
                inventario.getProveedor().getNombreProveedor()
        );

    }
}
