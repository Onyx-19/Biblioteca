package com.bibliored.demo.controller;

import com.bibliored.demo.dto.LibroRequestDTO;
import com.bibliored.demo.dto.LibroResponseDTO;
import com.bibliored.demo.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("libros")
public class LibroController {
    private LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @PostMapping
    public ResponseEntity <LibroResponseDTO> crear (@Valid @RequestBody LibroRequestDTO request){
        LibroResponseDTO creacion = libroService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creacion);
    }

    @GetMapping
    public ResponseEntity <List<LibroResponseDTO>> listarTodos(){
        return ResponseEntity.ok(libroService.listarTodos());
    }

    @GetMapping ("/{id}")
    public ResponseEntity <LibroResponseDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(libroService.obtenerPorId(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity <LibroResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody LibroRequestDTO request){
        return ResponseEntity.ok(libroService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
