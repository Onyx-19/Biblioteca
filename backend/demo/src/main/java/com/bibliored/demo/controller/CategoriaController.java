package com.bibliored.demo.controller;

import com.bibliored.demo.dto.CategoriaRequestDTO;
import com.bibliored.demo.dto.CategoriaResponseDTO;
import com.bibliored.demo.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping ("categorias")
public class CategoriaController {
    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crear (@Valid @RequestBody CategoriaRequestDTO request){
        CategoriaResponseDTO creacion = categoriaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creacion);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @GetMapping
    public ResponseEntity <List<CategoriaResponseDTO>> listarTodas (){
        return ResponseEntity.ok(categoriaService.ListarTodas());
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @GetMapping ("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerPorId (@PathVariable Long id){
        return ResponseEntity.ok(categoriaService.obtenerPorID(id));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @PutMapping ("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar (@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO request){
        return ResponseEntity.ok(categoriaService.actualizar(id, request));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @DeleteMapping ("/{id}")
    public ResponseEntity <CategoriaResponseDTO> eliminar (@PathVariable Long id){
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
