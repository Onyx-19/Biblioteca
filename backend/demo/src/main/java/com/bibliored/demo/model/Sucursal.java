package com.bibliored.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "01_sucursales")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Long idSucursal;

    @Column(name = "nombre_sucursal")
    private String nombreSucursal;

//    @Column(name = "administrador_sucursal", nullable = false, length = 100)
//    private String administradorSucursal;

    @Column(name = "direccion_sucursal", nullable = false, length = 100)
    private String direccionSucursal;

    @Column(name = "correo_sucursal", nullable = false, length = 100)
    private String correoSucursal;

    @Column(name = "telefono_sucursal", nullable = false, length = 15)
    private String telefonoSucursal;

    public Sucursal(){
    }

    public Sucursal(Long idSucursal, String nombreSucursal, /*String administradorSucursal,*/ String direccionSucursal, String correoSucursal, String telefonoSucursal) {
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
//        this.administradorSucursal = administradorSucursal;
        this.direccionSucursal = direccionSucursal;
        this.correoSucursal = correoSucursal;
        this.telefonoSucursal = telefonoSucursal;
    }

    public Long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

//    public String getAdministradorSucursal() {
//        return administradorSucursal;
//    }
//
//    public void setAdministradorSucursal(String administradorSucursal) {
//        this.administradorSucursal = administradorSucursal;
//    }

    public String getDireccionSucursal() {
        return direccionSucursal;
    }

    public void setDireccionSucursal(String direccionSucursal) {
        this.direccionSucursal = direccionSucursal;
    }

    public String getCorreoSucursal() {
        return correoSucursal;
    }

    public void setCorreoSucursal(String correoSucursal) {
        this.correoSucursal = correoSucursal;
    }

    public String getTelefonoSucursal() {
        return telefonoSucursal;
    }

    public void setTelefonoSucursal(String telefonoSucursal) {
        this.telefonoSucursal = telefonoSucursal;
    }
}
