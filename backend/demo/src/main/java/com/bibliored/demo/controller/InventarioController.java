package com.bibliored.demo.controller;

import com.bibliored.demo.dto.InventarioRequestDTO;
import com.bibliored.demo.dto.InventarioResponseDTO;
import com.bibliored.demo.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventarios")
public class InventarioController {
    private InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @PostMapping
    ResponseEntity<InventarioResponseDTO> crear (@Valid @RequestBody InventarioRequestDTO request){
        InventarioResponseDTO creacion = inventarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creacion);
    }

    @GetMapping
    ResponseEntity <List<InventarioResponseDTO>> listarTodos () {
        return ResponseEntity.ok(inventarioService.listarTodos());
    }

    @GetMapping ("/{id}")
    ResponseEntity <InventarioResponseDTO> obtenerPorid(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<InventarioResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody InventarioRequestDTO request) {
        return ResponseEntity.ok(inventarioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<InventarioResponseDTO> eliminar(@PathVariable Long id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
