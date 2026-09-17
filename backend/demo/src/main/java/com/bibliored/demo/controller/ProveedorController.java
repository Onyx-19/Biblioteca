package com.bibliored.demo.controller;

import com.bibliored.demo.dto.ProveedorRequestDTO;
import com.bibliored.demo.dto.ProveedorResponseDTO;
import com.bibliored.demo.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("proveedores")
public class ProveedorController{
    private ProveedorService proveedorService;

    public ProveedorController (ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(@Valid @RequestBody ProveedorRequestDTO request){
        ProveedorResponseDTO creacion = proveedorService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creacion);
    }

    @GetMapping
    public ResponseEntity <List<ProveedorResponseDTO>> listarTodos (){
        return ResponseEntity.ok(proveedorService.listarTodos());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(proveedorService.obtenerPorId(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizar (@PathVariable Long id, @Valid @RequestBody ProveedorRequestDTO request){
        return ResponseEntity.ok(proveedorService.actualizar(id, request));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<ProveedorResponseDTO> eliminar (@PathVariable Long id){
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
