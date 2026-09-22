package com.bibliored.demo.controller;

import com.bibliored.demo.dto.PrestamoRequestDTO;
import com.bibliored.demo.dto.PrestamoResponseDTO;
import com.bibliored.demo.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("prestamos")
public class PrestamoController {
    private PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    ResponseEntity<PrestamoResponseDTO> crear(@Valid @RequestBody PrestamoRequestDTO request) {
        PrestamoResponseDTO creacion = prestamoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creacion);
    }

    @GetMapping
    ResponseEntity <List<PrestamoResponseDTO>> listarTodos () {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    @GetMapping ("/{id}")
    ResponseEntity<PrestamoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.obtenerPorId(id));
    }

    @PutMapping ("/{id}")
    ResponseEntity<PrestamoResponseDTO> actualizar (@PathVariable Long id, @Valid @RequestBody PrestamoRequestDTO request) {
        return ResponseEntity.ok(prestamoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<PrestamoResponseDTO> eliminar(@PathVariable Long id) {
        prestamoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
