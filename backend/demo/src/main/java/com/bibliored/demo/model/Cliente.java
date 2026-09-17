package com.bibliored.demo.model;

import jakarta.persistence.*;

@Entity
@Table (name = "03_clientes")
public class Cliente {
    @Id
    @Column (name = "id_cliente")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idCliente;
    @Column (name = "identificacion_cliente", nullable = false, length = 50)
    private String identificacionCliente;
    @Column (name = "nombre_cliente", nullable = false, length = 200)
    private String nombreCliente;
    @Column (name = "direccion_cliente", length = 100)
    private String direccionCliente;
    @Column (name = "correo_cliente", nullable = false, length = 100)
    private String correoCliente;
    @Column (name = "telefono_cliente", nullable = false, length = 15)
    private String telefonoCliente;

    public Cliente(Long idCliente, String identificacionCliente, String nombreCliente, String direccionCliente, String correoCliente, String telefonoCliente) {
        this.idCliente = idCliente;
        this.identificacionCliente = identificacionCliente;
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.correoCliente = correoCliente;
        this.telefonoCliente = telefonoCliente;
    }

    public Cliente() {
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdentificacionCliente() {
        return identificacionCliente;
    }

    public void setIdentificacionCliente(String identificacionCliente) {
        this.identificacionCliente = identificacionCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }
}
