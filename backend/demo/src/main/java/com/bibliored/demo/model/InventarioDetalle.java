package com.bibliored.demo.model;

import jakarta.persistence.*;

@Entity
@Table (name = "09_inventario_detalle")
public class InventarioDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_detalle")
    private Long idDetalleInventario;
    @ManyToOne
    @JoinColumn(name = "id_transaccion")
    private Inventario idTransaccion;
    @ManyToOne
    @JoinColumn(name = "id_libro")
    private Libro idLibro;
    @Column (name = "unidades", nullable = false)
    private Integer unidades;

    public InventarioDetalle(Long idDetalleInventario,
                             Inventario idTransaccion,
                             Libro idLibro,
                             Integer unidades) {
        this.idDetalleInventario = idDetalleInventario;
        this.idTransaccion = idTransaccion;
        this.idLibro = idLibro;
        this.unidades = unidades;
    }

    public InventarioDetalle(){
    }

    public Long getIdDetalleInventario() {
        return idDetalleInventario;
    }

    public void setIdDetalleInventario(Long idDetalleInventario) {
        this.idDetalleInventario = idDetalleInventario;
    }

    public Inventario getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Inventario idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Libro getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Libro idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }
}
