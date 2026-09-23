package com.bibliored.demo.service;

import com.bibliored.demo.dto.PrestamoRequestDTO;
import com.bibliored.demo.dto.PrestamoResponseDTO;
import com.bibliored.demo.model.*;
import com.bibliored.demo.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService{
    private final PrestamoRepository prestamoRepository;
    private final SucursalRepository sucursalRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final ClienteRepository clienteRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository,
                               SucursalRepository sucursalRepository,
                               UsuarioRepository usuarioRepository,
                               LibroRepository libroRepository,
                               ClienteRepository clienteRepository
    ) {
        this.prestamoRepository = prestamoRepository;
        this.sucursalRepository = sucursalRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.clienteRepository = clienteRepository;
    }


    @Override
    public PrestamoResponseDTO crear(PrestamoRequestDTO request) {
        Prestamo prestamo = new Prestamo();
        Sucursal sucursal = buscarSucursal(request.idSucursal());
        Usuario usuario = buscarUsuario(request.idUsuario());
        Libro libro = buscarLibro(request.idLibro());
        Cliente cliente = buscarCliente(request.idCliente());

        prestamo.setTipoTransaccion(request.tipoTransaccion());
        prestamo.setUnidades(request.unidades());
        prestamo.setFechaRegistro(LocalDateTime.now());
        prestamo.setEstadoPrestamo(request.estadoPrestamo());
        prestamo.setFechaDevolucionEstimada(request.fechaDevolucionEstimada());
        prestamo.setFechaDevolucion(request.fechaDevolucion());
        prestamo.setSucursal(sucursal);
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setCliente(cliente);

        Prestamo guardado = prestamoRepository.save(prestamo);
        return toResponseDTO(guardado);
    }

    @Override
    public List<PrestamoResponseDTO> listarTodos() {
        return prestamoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public PrestamoResponseDTO obtenerPorId(Long id) {
        Prestamo prestamo = validacionPorId(id);
        return toResponseDTO(prestamo);
    }

    @Override
    public PrestamoResponseDTO actualizar(Long id, PrestamoRequestDTO request) {
        Prestamo prestamo = validacionPorId(id);
        Sucursal sucursal = buscarSucursal(request.idSucursal());
        Usuario usuario = buscarUsuario(request.idUsuario());
        Libro libro = buscarLibro(request.idLibro());
        Cliente cliente = buscarCliente(request.idCliente());

        prestamo.setTipoTransaccion(request.tipoTransaccion());
        prestamo.setUnidades(request.unidades());
        prestamo.setFechaRegistro(LocalDateTime.now());
        prestamo.setEstadoPrestamo(request.estadoPrestamo());
        prestamo.setFechaDevolucionEstimada(request.fechaDevolucionEstimada());
        prestamo.setFechaDevolucion(request.fechaDevolucion());
        prestamo.setSucursal(sucursal);
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setCliente(cliente);

        Prestamo actualizado = prestamoRepository.save(prestamo);
        return toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Prestamo prestamo = validacionPorId(id);
        prestamoRepository.delete(prestamo);
    }


    private Prestamo validacionPorId(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No se ha encontrado un prestamo con id: " + id));
    }

    private Sucursal buscarSucursal (Long id){
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado una sucursal con id: " + id));
    }

    private Usuario buscarUsuario (Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado un usuario con id: " + id));
    }

    private Libro buscarLibro (Long id){
        return libroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado un libro con id: " + id));
    }

    private Cliente buscarCliente (Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado un cliente con id: " + id));
    }

    private PrestamoResponseDTO toResponseDTO(Prestamo request) {
        return new PrestamoResponseDTO(
                request.getIdTransaccion(),
                request.getTipoTransaccion(),
                request.getUnidades(),
                request.getFechaRegistro(),
                request.getEstadoPrestamo(),
                request.getFechaDevolucionEstimada(),
                request.getFechaDevolucion(),
                request.getSucursal().getIdSucursal(),
                request.getSucursal().getNombreSucursal(),
                request.getUsuario().getIdUsuario(),
                request.getUsuario().getNombreUsuario(),
                request.getLibro().getIdLibro(),
                request.getLibro().getTituloLibro(),
                request.getCliente().getIdCliente(),
                request.getCliente().getNombreCliente()
        );
    }
}