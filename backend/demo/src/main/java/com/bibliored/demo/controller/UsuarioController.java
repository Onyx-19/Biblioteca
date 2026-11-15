package com.bibliored.demo.controller;


import com.bibliored.demo.dto.*;
import com.bibliored.demo.enums.Roles;
import com.bibliored.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER')")
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear (@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO creacion = usuarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creacion);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @GetMapping ("/{id}")
    public ResponseEntity <UsuarioResponseDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER')")
    @PutMapping ("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar (@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO request){
            return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER')")
    @DeleteMapping ("/{id}")
    public ResponseEntity<UsuarioResponseDTO> eliminar (@PathVariable Long id){
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> cambiarPassword(@PathVariable Long id, @Valid @RequestBody UsuarioPassinicialDTO request){
        usuarioService.cambiarPassword(id, request);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER')")
    @GetMapping ("/roles")
    public ResponseEntity <List<String>> roles() {
        List <String> roles = Arrays.stream(Roles.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(roles);
    }
}
