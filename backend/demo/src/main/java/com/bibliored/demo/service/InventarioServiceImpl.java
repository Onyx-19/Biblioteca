package com.bibliored.demo.service;

import com.bibliored.demo.dto.*;
import com.bibliored.demo.enums.MotivoMovimiento;
import com.bibliored.demo.enums.MovimientoInventario;
import com.bibliored.demo.exceptions.BusinessExceptions;
import com.bibliored.demo.model.*;
import com.bibliored.demo.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.bibliored.demo.enums.MotivoMovimiento.*;

@Service
public class InventarioServiceImpl implements InventarioService{
    private final InventarioRepository inventarioRepository;
    private final SucursalRepository sucursalRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProveedorRepository proveedorRepository;
    private final LibroRepository libroRepository;
    private final InventarioDetalleRepository inventarioDetalleRepository;
    private final InventarioStockRepository inventarioStockRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository,
                                 SucursalRepository sucursalRepository,
                                 UsuarioRepository usuarioRepository,
                                 ProveedorRepository proveedorRepository,
                                 LibroRepository libroRepository,
                                 InventarioDetalleRepository inventarioDetalleRepository,
                                 InventarioStockRepository inventarioStockRepository
    ) {
        this.inventarioRepository = inventarioRepository;
        this.sucursalRepository = sucursalRepository;
        this.usuarioRepository = usuarioRepository;
        this.proveedorRepository = proveedorRepository;
        this.libroRepository = libroRepository;
        this.inventarioDetalleRepository = inventarioDetalleRepository;
        this.inventarioStockRepository = inventarioStockRepository;
    }

    private void validarEncabezado(InventarioRequestDTO request){
        MotivoMovimiento motivo = request.motivoMovimiento();

        Set<MotivoMovimiento> permitidos = request.movimientoInventario() == MovimientoInventario.ENTRADA
                ? EnumSet.of(COMPRA, DONACION)
                : EnumSet.of(PRESTAMO, PERDIDA, DANO);

        if (!permitidos.contains(motivo)){
            throw new BusinessExceptions("El motivo no corresponde al tipo de movimiento");
        }

        boolean requiereProveedor = motivo == COMPRA || motivo == DONACION;
        if (requiereProveedor && request.idProveedor() == null) {
            throw new BusinessExceptions("El proveedor es obligatorio para las entradas de inventario.");
        }
    }

    private void movilizarStock(Libro libro, Sucursal sucursal, int unidades, InventarioRequestDTO request) {
        boolean entrada = request.movimientoInventario() == MovimientoInventario.ENTRADA;
        int variacion = entrada ? unidades : -unidades;

        InventarioStock stock = inventarioStockRepository.findBySucursalAndLibro(sucursal, libro)
                .orElseGet(() -> new InventarioStock (sucursal, libro, 0));

        int sinStock = stock.getDisponible() + variacion;
        if (sinStock < 0){
            throw new BusinessExceptions("Stock insuficiente para el libro: " + libro.getTituloLibro());
        }

        stock.setDisponible(sinStock);
        inventarioStockRepository.save(stock);
    }

    @Override
    @Transactional
    public InventarioResponseDTO crear(InventarioRequestDTO request){
        validarEncabezado(request);

        Set<Long> vistos = new HashSet<>();
        for (var item : request.items()) {
            if (!vistos.add(item.idLibro())) {
                throw new BusinessExceptions("El libro esta repetido en el documento.");
            }
        }

        Sucursal sucursal = buscarSucursal(request.idSucursal());
        Inventario inventario = new Inventario();
        inventario.setMovimientoInventario(request.movimientoInventario());
        inventario.setMotivoMovimiento(request.motivoMovimiento());
        inventario.setFechaHora(request.fechaHora());
        inventario.setSucursal(sucursal);
        inventario.setUsuario(buscarUsuario(request.idUsuario()));
        if(request.idProveedor() != null){
            inventario.setProveedor(buscarProveedor(request.idProveedor()));
        }

        for (var item : request.items()) {
            Libro libro = libroRepository.findById(item.idLibro())
                    .orElseThrow(() -> new BusinessExceptions("El libro no existe"));

            movilizarStock(libro, sucursal, item.unidades(), request);

            InventarioDetalle detalle = new InventarioDetalle();
            detalle.setIdTransaccion(inventario);
            detalle.setIdLibro(libro);
            detalle.setUnidades(item.unidades());
            inventario.getItems().add(detalle);
        }

        return toResponseDTO(inventarioRepository.save(inventario));
    };

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
    public List<InventarioDetalleResponseDTO> consultarInventarioDetalle(Long id) {
        return inventarioDetalleRepository.findByIdTransaccion_IdTransaccion(id)
                .stream()
                .map(detalle -> new InventarioDetalleResponseDTO(
                        detalle.getIdDetalleInventario(),
                        detalle.getIdTransaccion().getIdTransaccion(),
                        detalle.getIdLibro().getIdLibro(),
                        detalle.getIdLibro().getTituloLibro(),
                        detalle.getUnidades()
                ))
                .toList();
    }

    @Override
    public List<InventarioStock> consultarStock() {
        return inventarioStockRepository.findAll();
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
                inventario.getProveedor() != null ? inventario.getProveedor().getIdProveedor() : null,
                inventario.getProveedor() != null ? inventario.getProveedor().getNombreProveedor() : null,
                inventario.getItems()
                        .stream()
                        .map(this::toResponseDetalleDTO)
                        .toList()
        );
    }

    private InventarioDetalleResponseDTO toResponseDetalleDTO(InventarioDetalle request){
        return new InventarioDetalleResponseDTO(
                request.getIdDetalleInventario(),
                request.getIdTransaccion().getIdTransaccion(),
                request.getIdLibro().getIdLibro(),
                request.getIdLibro().getTituloLibro(),
                request.getUnidades()
        );
    }
}
