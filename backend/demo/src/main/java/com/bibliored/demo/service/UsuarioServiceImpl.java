package com.bibliored.demo.service;

import com.bibliored.demo.component.ValidadorNegocio;
import com.bibliored.demo.dto.UsuarioPassinicialDTO;
import com.bibliored.demo.dto.UsuarioRequestDTO;
import com.bibliored.demo.dto.UsuarioResponseDTO;
import com.bibliored.demo.model.Usuario;
import com.bibliored.demo.repository.UsuarioRepository;
import com.bibliored.demo.model.Sucursal;
import com.bibliored.demo.repository.SucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;
    private final SucursalRepository sucursalRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidadorNegocio validadorNegocio;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              SucursalRepository sucursalRepository,
                              PasswordEncoder passwordEncoder,
                              ValidadorNegocio validadorNegocio) {
        this.usuarioRepository = usuarioRepository;
        this.sucursalRepository = sucursalRepository;
        this.passwordEncoder = passwordEncoder;
        this.validadorNegocio = validadorNegocio;
    }

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO request) {
        Usuario usuario = new Usuario();
        Sucursal sucursal = buscarSucursal(request.idSucursal());
        String identificacion = request.identificacionUsuario();
        String nombre = validadorNegocio.formatoNombre(request.nombreUsuario());
        String correo = validadorNegocio.formatoCorreo(request.correoUsuario());

        validadorNegocio.validarIdentificacionDuplicadoUsuario(identificacion, null);
        validadorNegocio.validarCorreoDuplicadoUsuario(correo, null);

        usuario.setIdentificacionUsuario(identificacion);
        usuario.setNombreUsuario(nombre);
        usuario.setCorreoUsuario(correo);
        usuario.setContrasena(passwordEncoder.encode(request.identificacionUsuario()));
        usuario.setDireccionUsuario(request.direccionUsuario());
        usuario.setTelefonoUsuario(request.telefonoUsuario());
        usuario.setRolUsuario(request.rolUsuario());
        usuario.setSucursal(sucursal);
        usuario.setCambioPassInicial(true);

        Usuario guardado = usuarioRepository.save(usuario);
        return toResponseDTO(guardado);
    }

    @Override
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = validacionPorId(id);
        return toResponseDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO request) {
        Usuario usuario = validacionPorId(id);
        Sucursal sucursal = buscarSucursal(request.idSucursal());

        String identificacion = request.identificacionUsuario();
        String nombre = validadorNegocio.formatoNombre(request.nombreUsuario());
        String correo = validadorNegocio.formatoCorreo(request.correoUsuario());

        validadorNegocio.validarIdentificacionDuplicadoUsuario(identificacion, null);
        validadorNegocio.validarCorreoDuplicadoUsuario(correo, null);

        usuario.setIdentificacionUsuario(identificacion);
        usuario.setNombreUsuario(nombre);
        usuario.setCorreoUsuario(correo);
        usuario.setDireccionUsuario(request.direccionUsuario());
        usuario.setTelefonoUsuario(request.telefonoUsuario());
        usuario.setRolUsuario(request.rolUsuario());
        usuario.setSucursal(sucursal);

        Usuario actualizado = usuarioRepository.save(usuario);
        return toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = validacionPorId(id);
        usuarioRepository.delete(usuario);
    }

    @Override
    public void cambiarPassword(Long id, UsuarioPassinicialDTO request) {
        Usuario usuario = validacionPorId(id);
        usuario.setContrasena(passwordEncoder.encode(request.contrasena()));
        usuario.setCambioPassInicial(false);
        usuarioRepository.save(usuario);
    }

    private Usuario validacionPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron usuarios con id:" + id));
    }

    private Sucursal buscarSucursal(Long id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron sucursales con id: " + id));
    }

    private UsuarioResponseDTO toResponseDTO (Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getIdentificacionUsuario(),
                usuario.getNombreUsuario(),
                usuario.getCorreoUsuario(),
                usuario.getDireccionUsuario(),
                usuario.getTelefonoUsuario(),
                usuario.getRolUsuario(),
                usuario.getSucursal().getIdSucursal(),
                usuario.getSucursal().getNombreSucursal()
        );
    }
}