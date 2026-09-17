package com.bibliored.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "02_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_usuario" )
    private Long idUsuario;
    @Column (name = "identificacion_usuario", nullable = false, length = 50)
    private String identificacionUsuario ;
    @Column (name = "nombre_usuario", nullable = false, length = 100)
    private String nombreUsuario;
    @Column (name = "correo_usuario", nullable = false, length = 100)
    private String correoUsuario;
    @Column (name = "contrasena", nullable = false, length = 150)
    private String contrasena;
    @Column (name = "direccion_usuario", length = 100)
    private String direccionUsuario;
    @Column (name = "telefono_usuario", length = 15)
    private String telefonoUsuario;
    @Column (name = "rol_usuario", nullable = false, length = 50)
    private String rolUsuario;
    @ManyToOne
    @JoinColumn (name = "id_sucursal")
    private Sucursal sucursal;
    @Column(name = "cambio_pass_inicial")
    private boolean cambioPassInicial = true;

    public Usuario(Long idUsuario, String identificacionUsuario, String nombreUsuario, String correoUsuario, String contrasena, String direccionUsuario, String telefonoUsuario, String rolUsuario, Sucursal sucursal) {
        this.idUsuario = idUsuario;
        this.identificacionUsuario = identificacionUsuario;
        this.nombreUsuario = nombreUsuario;
        this.correoUsuario = correoUsuario;
        this.contrasena = contrasena;
        this.direccionUsuario = direccionUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.rolUsuario = rolUsuario;
        this.sucursal = sucursal;
    }

    public Usuario(){
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdentificacionUsuario() {
        return identificacionUsuario;
    }

    public void setIdentificacionUsuario(String identificacionUsuario) {
        this.identificacionUsuario = identificacionUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDireccionUsuario() {
        return direccionUsuario;
    }

    public void setDireccionUsuario(String direccionUsuario) {
        this.direccionUsuario = direccionUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public boolean isCambioPassInicial() {
        return cambioPassInicial;
    }

    public void setCambioPassInicial(boolean cambioPassInicial) {
        this.cambioPassInicial = cambioPassInicial;
    }
}