package com.bibliored.demo.controller;

import com.bibliored.demo.dto.AutorRequestDTO;
import com.bibliored.demo.dto.AutorResponseDTO;
import com.bibliored.demo.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("autores")
public class AutorController {
    private AutorService autorService;

    public AutorController (AutorService autorService){
        this.autorService = autorService;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @PostMapping
    public ResponseEntity<AutorResponseDTO> crear (@Valid @RequestBody AutorRequestDTO request){
        AutorResponseDTO creacion = autorService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creacion);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @GetMapping
    public ResponseEntity <List<AutorResponseDTO>> listarTodos (){
        return ResponseEntity.ok(autorService.listarTodos());
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @GetMapping ("/{id}")
    public ResponseEntity <AutorResponseDTO> obtenerPorId (@PathVariable Long id){
        return ResponseEntity.ok(autorService.obtenerPorID(id));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @PutMapping ("/{id}")
    public ResponseEntity<AutorResponseDTO> actualizar (@PathVariable Long id, @Valid @RequestBody AutorRequestDTO request){
        return ResponseEntity.ok(autorService.actualizar(id, request));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @DeleteMapping ("/{id}")
    public ResponseEntity<AutorResponseDTO> eliminar (@PathVariable Long id){
        autorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
