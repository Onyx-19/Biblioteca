package com.bibliored.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "04_proveedores")
public class Proveedor {
    @Id
    @Column (name = "id_proveedor")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idProveedor;
    @Column (name = "identificacion_proveedor", nullable = false, length = 50)
    private String identificacionProveedor;
    @Column (name = "nombre_proveedor", nullable = false, length = 100)
    private String nombreProveedor;
    @Column (name = "direccion_proveedor", length = 100)
    private String direccionProveedor;
    @Column (name = "correo_proveedor", nullable = false, length = 100)
    private String correoProveedor;
    @Column (name = "telefono_proveedor", length = 15)
    private String telefonoProveedor;

    public Proveedor(Long idProveedor, String identificacionProveedor, String nombreProveedor, String direccionProveedor, String correoProveedor, String telefonoProveedor) {
        this.idProveedor = idProveedor;
        this.identificacionProveedor = identificacionProveedor;
        this.nombreProveedor = nombreProveedor;
        this.direccionProveedor = direccionProveedor;
        this.correoProveedor = correoProveedor;
        this.telefonoProveedor = telefonoProveedor;
    }

    public Proveedor(){
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getIdentificacionProveedor() {
        return identificacionProveedor;
    }

    public void setIdentificacionProveedor(String identificacionProveedor) {
        this.identificacionProveedor = identificacionProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getCorreoProveedor() {
        return correoProveedor;
    }

    public void setCorreoProveedor(String correoProveedor) {
        this.correoProveedor = correoProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }
}