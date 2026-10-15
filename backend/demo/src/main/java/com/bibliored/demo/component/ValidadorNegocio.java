package com.bibliored.demo.component;

import com.bibliored.demo.exceptions.BusinessExceptions;
import com.bibliored.demo.model.*;
import com.bibliored.demo.repository.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ValidadorNegocio {
    private final SucursalRepository sucursalRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProveedorRepository proveedorRepository;
    private final ClienteRepository clienteRepository;


    public ValidadorNegocio (SucursalRepository sucursalRepository,
                             UsuarioRepository usuarioRepository,
                             CategoriaRepository categoriaRepository,
                             ProveedorRepository proveedorRepository,
                             ClienteRepository clienteRepository){
        this.sucursalRepository = sucursalRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.proveedorRepository = proveedorRepository;
        this.clienteRepository = clienteRepository;
    }

//  GLOBALES
    public String formatoNombre (String nombre){
        if (nombre == null || nombre.isEmpty()) return nombre;
        return Arrays.stream(nombre.trim().split("\\s+"))
                .map(palabra -> palabra.substring(0, 1).toUpperCase()+ palabra.substring(1)
                        .toLowerCase())
                .collect(Collectors.joining(" "));
    }

    public String formatoCorreo(String correo) {
        return correo == null ? "" : correo.toLowerCase();
    }

//  SUCURSALES
    public void validarNombreDuplicadoSucursal (String nombre, Long idExcluido){
        Optional<Sucursal> existeNombre = sucursalRepository.findByNombreSucursal(nombre);
        if (existeNombre.isPresent() && !existeNombre.get().getIdSucursal().equals(idExcluido)) {
            throw new BusinessExceptions("El nombre registrado ya ha sido utilizado para una sucursal.");
        }
    }

    public void validarCorreoDuplicadoSucursal (String correo, Long idExcluido){
        Optional<Sucursal> existeCorreo = sucursalRepository.findByCorreoSucursal(correo);
        if (existeCorreo.isPresent() && !existeCorreo.get().getIdSucursal().equals(idExcluido)) {
            throw new BusinessExceptions("El correo registrado ya ha sido utilizado para una sucursal.");
        }
    }

//  USUARIOS
    public void validarIdentificacionDuplicadoUsuario(String identificacion, Long idExcluido) {
        Optional<Usuario> existeIdentificacion = usuarioRepository.findByIdentificacionUsuario(identificacion);
        if (existeIdentificacion.isPresent() && !existeIdentificacion.get().getIdUsuario().equals(idExcluido)) {
            throw new BusinessExceptions("El numero de identificacion ya ha sido registrado previamente.");
        }
    }

    public void validarCorreoDuplicadoUsuario (String correo, Long idExcluido){
        Optional<Usuario> existeCorreo = usuarioRepository.findByCorreoUsuario(correo);
        if (existeCorreo.isPresent() && !existeCorreo.get().getIdUsuario().equals(idExcluido)) {
            throw new BusinessExceptions("El correo registrado ya ha sido utilizado para un usuario.");
        }
    }

//    CATEGORIAS
    public void validarNombreDuplicadoCategoria (String nombre, Long idExcluido){
        Optional<Categoria> existeNombre = categoriaRepository.findByNombreCategoria(nombre);
        if (existeNombre.isPresent() && !existeNombre.get().getIdCategoria().equals(idExcluido)) {
            throw new BusinessExceptions("La categoria ya ha sido registrada previamente.");
        }
    }

//  PROVEEDORES
    public void validarIdentificacionDuplicadoProveedor(String identificacion, Long idExcluido) {
        Optional<Proveedor> existeIdentificacion = proveedorRepository.findByIdentificacionProveedor(identificacion);
        if (existeIdentificacion.isPresent() && !existeIdentificacion.get().getIdProveedor().equals(idExcluido)) {
            throw new BusinessExceptions("El numero de identificacion ya ha sido registrado previamente.");
        }
    }

    public void validarCorreoDuplicadoProveedor (String correo, Long idExcluido){
        Optional<Proveedor> existeCorreo = proveedorRepository.findByCorreoProveedor(correo);
        if (existeCorreo.isPresent() && !existeCorreo.get().getIdProveedor().equals(idExcluido)) {
            throw new BusinessExceptions("El correo registrado ya ha sido utilizado para un proveedor.");
        }
    }

//  CLIENTES
    public void validarIdentificacionDuplicadoCliente(String identificacion, Long idExcluido) {
        Optional<Cliente> existeIdentificacion = clienteRepository.findByIdentificacionCliente(identificacion);
        if (existeIdentificacion.isPresent() && !existeIdentificacion.get().getIdCliente().equals(idExcluido)) {
            throw new BusinessExceptions("El numero de identificacion ya ha sido registrado previamente.");
        }
    }

    public void validarCorreoDuplicadoCliente (String correo, Long idExcluido){
        Optional<Cliente> existeCorreo = clienteRepository.findByCorreoCliente(correo);
        if (existeCorreo.isPresent() && !existeCorreo.get().getIdCliente().equals(idExcluido)) {
            throw new BusinessExceptions("El correo registrado ya ha sido utilizado para un cliente.");
        }
    }

}