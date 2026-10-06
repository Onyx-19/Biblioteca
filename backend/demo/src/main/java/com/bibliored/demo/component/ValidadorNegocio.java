package com.bibliored.demo.component;

import com.bibliored.demo.exceptions.BusinessExceptions;
import com.bibliored.demo.model.Sucursal;
import com.bibliored.demo.repository.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ValidadorNegocio {
    private final SucursalRepository sucursalRepository;

    public ValidadorNegocio (SucursalRepository sucursalRepository){
        this.sucursalRepository = sucursalRepository;
    }

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

    public void validarNombreDuplicado (String nombre, Long idExcluido, String entidad){
        Optional<Sucursal> existeNombre = sucursalRepository.findByNombreSucursal(nombre);
        if (existeNombre.isPresent() && !existeNombre.get().getIdSucursal().equals(idExcluido)) {
            throw new BusinessExceptions("El nombre registrado ya ha sido utilizado para un " + entidad);
        }
    }

    public void validarCorreoDuplicado (String correo, Long idExcluido, String entidad){
        Optional<Sucursal> existeCorreo = sucursalRepository.findByCorreoSucursal(correo);
        if (existeCorreo.isPresent() && !existeCorreo.get().getIdSucursal().equals(idExcluido)) {
            throw new BusinessExceptions("El correo registrado ya ha sido utilizado para un " + entidad);
        }
    }
}