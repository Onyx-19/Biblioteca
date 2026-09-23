package com.bibliored.demo.config;

import com.bibliored.demo.model.Sucursal;
import com.bibliored.demo.model.Usuario;
import com.bibliored.demo.repository.SucursalRepository;
import com.bibliored.demo.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final SucursalRepository sucursalRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(SucursalRepository sucursalRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.sucursalRepository = sucursalRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run (String... args){
        if (usuarioRepository.count() > 0) return;

        Sucursal matriz = new Sucursal();
        matriz.setNombreSucursal("Matriz");
        matriz.setDireccionSucursal("null");
        matriz.setCorreoSucursal("null");
        matriz.setTelefonoSucursal("null");
        Sucursal matrizInicial = sucursalRepository.save(matriz);

        Usuario usuario = new Usuario();
        usuario.setIdentificacionUsuario("900152284");
        usuario.setNombreUsuario("ADMINISTRADOR");
        usuario.setCorreoUsuario("null");
        usuario.setContrasena(passwordEncoder.encode("900152284"));
        usuario.setRolUsuario("ADMINISTRADOR");
        usuario.setCambioPassInicial(true);
        usuario.setSucursal(matrizInicial);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
    }
}