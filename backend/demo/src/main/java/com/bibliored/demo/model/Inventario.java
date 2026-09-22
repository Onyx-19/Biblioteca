package com.bibliored.demo.model;

import com.bibliored.demo.enums.TransaccionInventario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "08_inventarios")
public class Inventario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_transaccion")
    private Long idTransaccion;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaccion_inventario", nullable = false)
    private TransaccionInventario transaccionInventario;
    @Column(name = "unidades", nullable = false)
    private Integer unidades;
    @Column(name = "valor_libro", nullable = false)
    private Integer valorLibro;
    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_libro")
    private Libro libro;
    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    public Inventario(Long idTransaccion, TransaccionInventario transaccionInventario, Integer unidades, Integer valorLibro, LocalDateTime fechaHora, Sucursal sucursal, Usuario usuario, Libro libro, Proveedor proveedor) {
        this.idTransaccion = idTransaccion;
        this.transaccionInventario = transaccionInventario;
        this.unidades = unidades;
        this.valorLibro = valorLibro;
        this.fechaHora = fechaHora;
        this.sucursal = sucursal;
        this.usuario = usuario;
        this.libro = libro;
        this.proveedor = proveedor;
    }

    public Inventario (){
    }

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public TransaccionInventario getTransaccionInventario() {
        return transaccionInventario;
    }

    public void setTransaccionInventario(TransaccionInventario transaccionInventario) {
        this.transaccionInventario = transaccionInventario;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public Integer getValorLibro() {
        return valorLibro;
    }

    public void setValorLibro(Integer valorLibro) {
        this.valorLibro = valorLibro;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
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

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}