package com.bibliored.demo.model;

import jakarta.persistence.*;

@Entity
@Table (name = "12_inventario_detalle")
public class PrestamoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetallePrestamo;
    @ManyToOne
    @JoinColumn(name = "id_transaccion")
    private Prestamo idTransaccion;
    @ManyToOne
    @JoinColumn(name = "id_libro")
    private Libro idLibro;
    @Column (name = "unidades", nullable = false)
    private Integer unidades;

    public PrestamoDetalle(Prestamo idTransaccion,
                           Libro idLibro,
                           Integer unidades) {
        this.idTransaccion = idTransaccion;
        this.idLibro = idLibro;
        this.unidades = unidades;
    }

    public PrestamoDetalle() {
    }

    public Long getIdDetallePrestamo() {
        return idDetallePrestamo;
    }

    public void setIdDetallePrestamo(Long idDetallePrestamo) {
        this.idDetallePrestamo = idDetallePrestamo;
    }

    public Prestamo getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Prestamo idTransaccion) {
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
