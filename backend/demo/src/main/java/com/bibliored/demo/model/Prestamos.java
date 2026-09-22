package com.bibliored.demo.model;

import com.bibliored.demo.enums.EstadoPrestamo;
import com.bibliored.demo.enums.TipoTransaccion;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table (name = "09_prestamos")
public class Prestamos {
    private Long idTransaccion;
    private TipoTransaccion tipoTransaccion;
    private Integer unidades;
    private EstadoPrestamo estadoPrestamo;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucionEstimada;
    private LocalDateTime fechaDevolucion;

    private Sucursal sucursal;
    private Usuario usuario;
    private Libro libro;
    private Cliente cliente;



}
