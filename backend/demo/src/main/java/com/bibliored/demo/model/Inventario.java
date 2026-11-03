package com.bibliored.demo.model;

import com.bibliored.demo.enums.MotivoMovimiento;
import com.bibliored.demo.enums.MovimientoInventario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "08_inventarios")
public class Inventario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_transaccion")
    private Long idTransaccion;
    @Enumerated(EnumType.STRING)
    @Column(name = "movimiento_inventario", nullable = false)
    private MovimientoInventario movimientoInventario;
    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_movimiento", nullable = false)
    private MotivoMovimiento motivoMovimiento;
    @Column(name = "fecha_hora", nullable = false)
    private LocalDate fechaHora;

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "idTransaccion", cascade = CascadeType.ALL)
    private List<InventarioDetalle> items = new ArrayList<>();

    public Inventario(Long idTransaccion,
                      MovimientoInventario movimientoInventario,
                      MotivoMovimiento motivoMovimiento,
                      LocalDate fechaHora,
                      Sucursal sucursal,
                      Usuario usuario,
                      Proveedor proveedor,
                      List<InventarioDetalle> items ) {
        this.idTransaccion = idTransaccion;
        this.movimientoInventario = movimientoInventario;
        this.motivoMovimiento = motivoMovimiento;
        this.fechaHora = fechaHora;
        this.sucursal = sucursal;
        this.usuario = usuario;
        this.proveedor = proveedor;
        this.items = items;
    }

    public Inventario (){
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

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public MotivoMovimiento getMotivoMovimiento() {
        return motivoMovimiento;
    }

    public void setMotivoMovimiento(MotivoMovimiento motivoMovimiento) {
        this.motivoMovimiento = motivoMovimiento;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<InventarioDetalle> getItems() {
        return items;
    }

    public void setItems(List<InventarioDetalle> items) {
        this.items = items;
    }
}