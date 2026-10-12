package com.bibliored.demo.component;

import com.bibliored.demo.exceptions.BusinessExceptions;
import com.bibliored.demo.model.Sucursal;
import com.bibliored.demo.model.Usuario;
import com.bibliored.demo.repository.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ValidadorNegocio {
    private final SucursalRepository sucursalRepository;
    private final UsuarioRepository usuarioRepository;

    public ValidadorNegocio (SucursalRepository sucursalRepository,
                             UsuarioRepository usuarioRepository){
        this.sucursalRepository = sucursalRepository;
        this.usuarioRepository = usuarioRepository;
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

    // USUARIOS
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
}