package com.bibliored.demo.service;

import com.bibliored.demo.config.JwtService;
import com.bibliored.demo.dto.UsuarioAuthRequestDTO;
import com.bibliored.demo.dto.UsuarioAuthResponseDTO;
import com.bibliored.demo.dto.UsuarioResetPassRequestDTO;
import com.bibliored.demo.exceptions.BusinessExceptions;
import com.bibliored.demo.model.Usuario;
import com.bibliored.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioAuthServiceImpl implements UsuarioAuthService, UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername (String identificacionUsuario) throws UsernameNotFoundException{
        Usuario usuario = usuarioRepository.findByIdentificacionUsuario(identificacionUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con identificacion: " + identificacionUsuario));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRolUsuario().name());

        return new User(
                usuario.getIdentificacionUsuario(),
                usuario.getContrasena(),
                List.of(authority)
        );
    }

    @Override
    public UsuarioAuthResponseDTO login(UsuarioAuthRequestDTO request) {
        Usuario usuario = usuarioRepository.findByIdentificacionUsuario(request.identificacionUsuario())
                .orElseThrow(() -> new BusinessExceptions("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.contrasena(), usuario.getContrasena())){
            throw new BusinessExceptions("Contraseña incorrecta.");
        }

        UserDetails userDetails = loadUserByUsername(request.identificacionUsuario());
        String token = jwtService.generarToken(userDetails);

        return new UsuarioAuthResponseDTO(token, usuario.getIdUsuario(), usuario.getNombreUsuario(), usuario.getRolUsuario().name(), usuario.isCambioPassInicial());
    }

    @Override
    public void reset(UsuarioResetPassRequestDTO request) {
        Usuario usuario = usuarioRepository.findByIdentificacionUsuario(request.identificacionUsuario())
                .orElseThrow(() -> new BusinessExceptions("Usuario no encontrado"));

        if(!usuario.getCorreoUsuario().equals(request.correoUsuario())) {
            throw new BusinessExceptions("Los datos no coinciden");
        }

        usuario.setContrasena(passwordEncoder.encode(request.identificacionUsuario()));
        usuario.setCambioPassInicial(true);
        usuarioRepository.save(usuario);
    }
}
