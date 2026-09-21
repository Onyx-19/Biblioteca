package com.bibliored.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "08_inventarios")
public class Inventario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_transaccion")
    private Long idTransaccion;
    @Column(name = "tipo_transaccion", nullable = false, length = 100)
    private String tipoTransaccion;
    @Column(name = "unidades", nullable = false)
    private Integer unidades;
    @Column(name = "stock")
    private Integer stock;
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
}