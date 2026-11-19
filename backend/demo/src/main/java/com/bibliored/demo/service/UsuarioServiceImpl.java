package com.bibliored.demo.service;

import com.bibliored.demo.component.ValidadorNegocio;
import com.bibliored.demo.dto.*;
import com.bibliored.demo.enums.Roles;
import com.bibliored.demo.exceptions.BusinessExceptions;
import com.bibliored.demo.model.Usuario;
import com.bibliored.demo.repository.UsuarioRepository;
import com.bibliored.demo.model.Sucursal;
import com.bibliored.demo.repository.SucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

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

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioAuth = usuarioRepository.findByIdentificacionUsuario(user)
                .orElseThrow(() -> new BusinessExceptions("Usuario no encontrado"));

        validadorNegocio.validarIdentificacionDuplicadoUsuario(identificacion, null);
        validadorNegocio.validarCorreoDuplicadoUsuario(correo, null);
        validadorNegocio.validarRoles(usuarioAuth.getRolUsuario(), request.rolUsuario());

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

//        String identificacion = request.identificacionUsuario();
        String nombre = validadorNegocio.formatoNombre(request.nombreUsuario());
//        String correo = validadorNegocio.formatoCorreo(request.correoUsuario());

        String identificacion = vacioOEnmascarado(request.identificacionUsuario())
                ? usuario.getIdentificacionUsuario()
                : request.identificacionUsuario();
        String correo = vacioOEnmascarado(request.correoUsuario())
                ? usuario.getCorreoUsuario()
                : validadorNegocio.formatoCorreo(request.correoUsuario());

        if (usuario.getRolUsuario() == Roles.ADMINISTRADOR && request.rolUsuario() != Roles.ADMINISTRADOR) {
            throw new AccessDeniedException("No se puede modificar el rol de un Administrador.");
        }

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioAuth = usuarioRepository.findByIdentificacionUsuario(user)
                .orElseThrow(() -> new BusinessExceptions("Usuario no encontrado"));

        validadorNegocio.validarIdentificacionDuplicadoUsuario(identificacion, id);
        validadorNegocio.validarCorreoDuplicadoUsuario(correo, id);
        validadorNegocio.validarRoles(usuarioAuth.getRolUsuario(), request.rolUsuario());

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
    public void cambiarPasswordInicial(Long id, UsuarioPassInicialRequestDTO request) {

        String identificacion = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Usuario usuarioAuth = usuarioRepository.findByIdentificacionUsuario(identificacion)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuarioAuth.getIdUsuario().equals(id)) {
            throw new BusinessExceptions("No puedes cambiar la contraseña de otro usuario");
        }

        Usuario usuario = validacionPorId(id);
        usuario.setContrasena(passwordEncoder.encode(request.contrasena()));

        if (request.contrasena() == null || request.contrasena().length() < 8) {
            throw new BusinessExceptions("La nueva contraseña debe tener al menos 8 caracteres.");
        }
        usuario.setCambioPassInicial(false);
        usuarioRepository.save(usuario);
    }

    private Usuario usuarioActivo() {
        String identificacion = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByIdentificacionUsuario(identificacion)
                .orElseThrow(() -> new BusinessExceptions("Usuario no encontrado"));
    }

    @Override
    public UsuarioResponseDTO perfil() {
        return toPerfilDTO(usuarioActivo());
    }

    @Override
    public UsuarioResponseDTO actualizarPerfil(UsuarioPerfilRequestDTO request) {
        Usuario usuario = usuarioActivo();

        String nombre = validadorNegocio.formatoNombre(request.nombreUsuario());
        String correo = validadorNegocio.formatoCorreo(request.correoUsuario());

        validadorNegocio.validarCorreoDuplicadoUsuario(correo, usuario.getIdUsuario());

        usuario.setNombreUsuario(nombre);
        usuario.setCorreoUsuario(correo);
        usuario.setTelefonoUsuario(request.telefonoUsuario());
        usuario.setDireccionUsuario(request.direccionUsuario());

        return toPerfilDTO(usuarioRepository.save(usuario));
    }

    @Override
    public void cambioContrasena(UsuarioCambioPassRequestDTO request) {
        Usuario usuario = usuarioActivo();
        if (!passwordEncoder.matches(request.actual(), usuario.getContrasena())) {
            throw new BusinessExceptions("La contraseña actual no es correcta");
        }
        if (request.nueva() == null || request.nueva().length() < 8) {
            throw new BusinessExceptions("La nueva contraseña debe tener al menos 8 caracteres.");
        }
        usuario.setContrasena(passwordEncoder.encode(request.nueva()));
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

    private boolean vacioOEnmascarado(String valor) {
        return valor == null || valor.isBlank() || valor.contains("*");
    }

    public static String enmascararCorreo(String correo) {
        if (correo == null || !correo.contains("@")) return correo;
        int at = correo.indexOf("@");
        return correo.charAt(0) + "***" + correo.substring(at);
    }

    public static String enmascararIdentificacion(String id) {
        if (id == null || id.length() <= 4) return "****";
        return "*".repeat(id.length() - 4) + id.substring(id.length() - 4);
    }

    private UsuarioResponseDTO toResponseDTO (Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                enmascararIdentificacion(usuario.getIdentificacionUsuario()),
                usuario.getNombreUsuario(),
                enmascararCorreo(usuario.getCorreoUsuario()),
                usuario.getDireccionUsuario(),
                usuario.getTelefonoUsuario(),
                usuario.getRolUsuario(),
                usuario.getSucursal().getIdSucursal(),
                usuario.getSucursal().getNombreSucursal()
        );
    }

    private UsuarioResponseDTO toPerfilDTO(Usuario usuario) {
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