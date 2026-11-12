package com.bibliored.demo.service;

import com.bibliored.demo.dto.*;
import com.bibliored.demo.enums.EstadoPrestamo;
import com.bibliored.demo.enums.MotivoMovimiento;
import com.bibliored.demo.enums.MovimientoInventario;
import com.bibliored.demo.exceptions.BusinessExceptions;
import com.bibliored.demo.model.*;
import com.bibliored.demo.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PrestamoServiceImpl implements PrestamoService{
    private final PrestamoRepository prestamoRepository;
    private final SucursalRepository sucursalRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final ClienteRepository clienteRepository;
    private final PrestamoDetalleRepository prestamoDetalleRepository;
//    private final StockService stockService;
    private final InventarioService inventarioService;


    public PrestamoServiceImpl(PrestamoRepository prestamoRepository,
                               SucursalRepository sucursalRepository,
                               UsuarioRepository usuarioRepository,
                               LibroRepository libroRepository,
                               ClienteRepository clienteRepository,
                               PrestamoDetalleRepository prestamoDetalleRepository,
//                               StockService stockService,
                               InventarioService inventarioService
    ) {
        this.prestamoRepository = prestamoRepository;
        this.sucursalRepository = sucursalRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.clienteRepository = clienteRepository;
        this.prestamoDetalleRepository = prestamoDetalleRepository;
//        this.stockService = stockService;
        this.inventarioService = inventarioService;
    }


    @Override
    @Transactional
    public PrestamoResponseDTO crear(PrestamoRequestDTO request) {
        int DIAS_PRESTAMO = 15;

        Set<Long> vistos = new HashSet<>();
        for(var item: request.items()){
            if (!vistos.add(item.idLibro())){
                throw new BusinessExceptions("El libro esta repetido en el documento.");
            }
        }

        Sucursal sucursal = buscarSucursal(request.idSucursal());
        Prestamo prestamo = new Prestamo();
        prestamo.setMovimientoInventario(MovimientoInventario.ENTRADA);
        prestamo.setMotivoMovimiento(MotivoMovimiento.PRESTAMO);
        prestamo.setFechaRegistro(LocalDate.now());
        prestamo.setEstadoPrestamo(EstadoPrestamo.ACTIVO);
        prestamo.setSucursal(sucursal);
        prestamo.setUsuario(buscarUsuario(request.idUsuario()));
        prestamo.setCliente(buscarCliente(request.idCliente()));
        prestamo.setFechaDevolucionEstimada(LocalDate.now().plusDays(DIAS_PRESTAMO));

        for (var item : request.items()) {
            Libro libro = libroRepository.findById(item.idLibro())
                    .orElseThrow(() -> new BusinessExceptions("El libro no existe"));

            List<InventarioDetalleRequestDTO> listItems = request.items()
                    .stream()
                    .map(p -> new InventarioDetalleRequestDTO(p.idLibro(), p.unidades()))
                    .toList();

            inventarioService.crear(new InventarioRequestDTO(
                    MovimientoInventario.SALIDA,
                    MotivoMovimiento.PRESTAMO,
                    LocalDate.now(),
                    request.idSucursal(),
                    request.idUsuario(),
                    null,
                    listItems
            ));

            PrestamoDetalle detalle = new PrestamoDetalle();
            detalle.setIdTransaccion(prestamo);
            detalle.setIdLibro(libro);
            detalle.setUnidades(item.unidades());
            prestamo.getItems().add(detalle);
        }

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
    @Transactional
    public PrestamoResponseDTO cierrePrestamo (Long id){
        Prestamo prestamo = validacionPorId(id);

        if (prestamo.getEstadoPrestamo() == EstadoPrestamo.FINALIZADO){
            throw new BusinessExceptions("El prestamo ya ha sido devuelto previamente.");
        }

        List<InventarioDetalleRequestDTO> listItems = prestamo.getItems()
                .stream()
                .map(d -> new InventarioDetalleRequestDTO(d.getIdLibro().getIdLibro(),                        d.getUnidades()))
                .toList();

        inventarioService.crear(new InventarioRequestDTO(
                MovimientoInventario.ENTRADA,
                MotivoMovimiento.DEVOLUCION,
                LocalDate.now(),
                prestamo.getSucursal().getIdSucursal(),
                prestamo.getUsuario().getIdUsuario(),
                null,
                listItems
        ));

        prestamo.setEstadoPrestamo(EstadoPrestamo.FINALIZADO);
        prestamo.setFechaDevolucion(LocalDate.now());
        return toResponseDTO(prestamoRepository.save(prestamo));

    }

    @Override
    public List<PrestamoDetalleResponseDTO> consultarPrestamoDetalle(Long id){
        return prestamoDetalleRepository.findByIdTransaccion(id)
                .stream()
                .map(detalle -> new PrestamoDetalleResponseDTO(
                        detalle.getIdDetallePrestamo(),
                        detalle.getIdTransaccion().getIdTransaccion(),
                        detalle.getIdLibro().getIdLibro(),
                        detalle.getIdLibro().getTituloLibro(),
                        detalle.getUnidades()
                ))
                .toList();
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

    private PrestamoResponseDTO toResponseDTO(Prestamo prestamo) {
        return new PrestamoResponseDTO(
                prestamo.getIdTransaccion(),
                prestamo.getMovimientoInventario(),
                prestamo.getMotivoMovimiento(),
                prestamo.getFechaRegistro(),
                prestamo.getEstadoPrestamo(),
                prestamo.getFechaDevolucionEstimada(),
                prestamo.getFechaDevolucion(),
                prestamo.getSucursal().getIdSucursal(),
                prestamo.getSucursal().getNombreSucursal(),
                prestamo.getUsuario().getIdUsuario(),
                prestamo.getUsuario().getNombreUsuario(),
                prestamo.getCliente().getIdCliente(),
                prestamo.getCliente().getNombreCliente(),
                prestamo.getCliente().getIdentificacionCliente(),
                prestamo.getItems()
                        .stream()
                        .map(this::toResponseDetalleDTO)
                        .toList()
        );
    }

    private PrestamoDetalleResponseDTO toResponseDetalleDTO(PrestamoDetalle request) {
        return new PrestamoDetalleResponseDTO(
                request.getIdDetallePrestamo(),
                request.getIdTransaccion().getIdTransaccion(),
                request.getIdLibro().getIdLibro(),
                request.getIdLibro().getTituloLibro(),
                request.getUnidades()
        );
    }
}