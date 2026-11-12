package com.bibliored.demo.model;

import com.bibliored.demo.enums.EstadoPrestamo;
import com.bibliored.demo.enums.MotivoMovimiento;
import com.bibliored.demo.enums.MovimientoInventario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "11_prestamos")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Long idTransaccion;
    @Enumerated(EnumType.STRING)
    @Column(name = "movimiento_inventario", nullable = false)
    private MovimientoInventario movimientoInventario;
    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_movimiento", nullable = false)
    private MotivoMovimiento motivoMovimiento;
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_prestamo")
    private EstadoPrestamo estadoPrestamo;
    @Column(name = "fecha_devolucion_estimada")
    private LocalDate fechaDevolucionEstimada;
    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany (mappedBy = "idTransaccion", cascade = CascadeType.ALL)
    private List <PrestamoDetalle> items = new ArrayList<>();

    public Prestamo(Long idTransaccion,
                    MovimientoInventario movimientoInventario,
                    MotivoMovimiento motivoMovimiento,
                    LocalDate fecharegistro,
                    EstadoPrestamo estadoPrestamo,
                    LocalDate fechaDevolucionEstimada,
                    LocalDate fechaDevolucion,
                    Sucursal sucursal,
                    Usuario usuario,
                    Cliente cliente,
                    List<PrestamoDetalle> items ) {
        this.idTransaccion = idTransaccion;
        this.movimientoInventario = movimientoInventario;
        this.motivoMovimiento = motivoMovimiento;
        this.fechaRegistro = fecharegistro;
        this.estadoPrestamo = estadoPrestamo;
        this.fechaDevolucionEstimada = fechaDevolucionEstimada;
        this.fechaDevolucion = fechaDevolucion;
        this.sucursal = sucursal;
        this.usuario = usuario;
        this.cliente = cliente;
        this.items = items;
    }

    public Prestamo(){
    }

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public MovimientoInventario getMovimientoInventario() {
        return movimientoInventario;
    }

    public void setMovimientoInventario(MovimientoInventario movimientoInventario) {
        this.movimientoInventario = movimientoInventario;
    }

    public MotivoMovimiento getMotivoMovimiento() {
        return motivoMovimiento;
    }

    public void setMotivoMovimiento(MotivoMovimiento motivoMovimiento) {
        this.motivoMovimiento = motivoMovimiento;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public EstadoPrestamo getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(EstadoPrestamo estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }

    public LocalDate getFechaDevolucionEstimada() {
        return fechaDevolucionEstimada;
    }

    public void setFechaDevolucionEstimada(LocalDate fechaDevolucionEstimada) {
        this.fechaDevolucionEstimada = fechaDevolucionEstimada;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<PrestamoDetalle> getItems() {
        return items;
    }

    public void setItems(List<PrestamoDetalle> items) {
        this.items = items;
    }
}
