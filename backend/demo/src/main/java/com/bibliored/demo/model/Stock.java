package com.bibliored.demo.model;

import jakarta.persistence.*;

@Entity
@Table (name = "10_inventario_stock")
public class Stock {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock")
    private Long idStock;
    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;
    @ManyToOne
    @JoinColumn(name = "id_libro")
    private Libro libro;
    @Column(name = "disponible")
    private Integer disponible = 0;

    public Stock(Sucursal sucursal, Libro libro, Integer disponible) {
        this.sucursal = sucursal;
        this.libro = libro;
        this.disponible = disponible;
    }

    public Stock() {
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = this.sucursal;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Integer getDisponible() {
        return disponible;
    }

    public void setDisponible(Integer disponible) {
        this.disponible = disponible;
    }
}
