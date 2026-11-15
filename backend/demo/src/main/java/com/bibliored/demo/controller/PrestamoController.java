package com.bibliored.demo.controller;

import com.bibliored.demo.dto.PrestamoRequestDTO;
import com.bibliored.demo.dto.PrestamoResponseDTO;
import com.bibliored.demo.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping ("prestamos")
public class PrestamoController {
    private PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @PostMapping
    ResponseEntity<PrestamoResponseDTO> crear(@Valid @RequestBody PrestamoRequestDTO request) {
        PrestamoResponseDTO creacion = prestamoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creacion);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @GetMapping
    ResponseEntity <List<PrestamoResponseDTO>> listarTodos () {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @GetMapping ("/{id}")
    ResponseEntity<PrestamoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.obtenerPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERVISOR', 'MANAGER', 'BIBLIOTECARIO')")
    @PutMapping("/{id}/devolucion")
    ResponseEntity<PrestamoResponseDTO> finalizarPrestamo(@PathVariable Long id){
        return ResponseEntity.ok(prestamoService.cierrePrestamo(id));
    }
}
