package com.bibliored.demo.controller;


import com.bibliored.demo.dto.*;
import com.bibliored.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear (@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO creacion = usuarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creacion);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodas(){
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping ("/{id}")
    public ResponseEntity <UsuarioResponseDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar (@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO request){
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<UsuarioResponseDTO> eliminar (@PathVariable Long id){
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> cambiarPassword(@PathVariable Long id, @Valid @RequestBody UsuarioPassinicialDTO request){
        usuarioService.cambiarPassword(id, request);
        return ResponseEntity.noContent().build();
    }
}
