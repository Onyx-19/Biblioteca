package com.bibliored.demo.service;

import com.bibliored.demo.dto.InventarioDetalleResponseDTO;
import com.bibliored.demo.dto.InventarioRequestDTO;
import com.bibliored.demo.dto.InventarioResponseDTO;
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

    public InventarioServiceImpl(InventarioRepository inventarioRepository,
                                 SucursalRepository sucursalRepository,
                                 UsuarioRepository usuarioRepository,
                                 ProveedorRepository proveedorRepository,
                                 LibroRepository libroRepository
    ) {
        this.inventarioRepository = inventarioRepository;
        this.sucursalRepository = sucursalRepository;
        this.usuarioRepository = usuarioRepository;
        this.proveedorRepository = proveedorRepository;
        this.libroRepository = libroRepository;
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

        Inventario inventario = new Inventario();
        inventario.setMovimientoInventario(request.movimientoInventario());
        inventario.setMotivoMovimiento(request.motivoMovimiento());
        inventario.setFechaHora(request.fechaHora());
        inventario.setSucursal(buscarSucursal(request.idSucursal()));
        inventario.setUsuario(buscarUsuario(request.idUsuario()));
        if(request.idProveedor() != null){
            inventario.setProveedor(buscarProveedor(request.idProveedor()));
        }

        for (var item : request.items()) {
            Libro libro = libroRepository.findById(item.idLibro())
                    .orElseThrow(() -> new BusinessExceptions("El libro no existe"));
            InventarioDetalle detalle = new InventarioDetalle();
            detalle.setIdTransaccion(inventario);
            detalle.setIdLibro(libro);
            detalle.setUnidades(item.unidades());
            inventario.getItems().add(detalle);

//            actualizarStock(libro, inventario.getSucursal(), item.unidades(), dto.motivoMovimiento());
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
//                inventario.getProveedor().getIdProveedor(),
//                inventario.getProveedor().getNombreProveedor(),
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
