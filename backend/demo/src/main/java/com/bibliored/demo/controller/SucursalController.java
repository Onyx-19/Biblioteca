package com.bibliored.demo.controller;

import com.bibliored.demo.dto.SucursalRequestDTO;
import com.bibliored.demo.dto.SucursalResponseDTO;
import com.bibliored.demo.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sucursales")
public class SucursalController {
    private SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService){
        this.sucursalService = sucursalService;
    }

    @PostMapping
    public ResponseEntity<SucursalResponseDTO> crear(@Valid @RequestBody SucursalRequestDTO request){
        SucursalResponseDTO creacion = sucursalService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creacion);
    }

    @GetMapping
    public ResponseEntity<List<SucursalResponseDTO>> listarTodas(){
        return ResponseEntity.ok(sucursalService.listarTodas());
    }

    @GetMapping ("/{id}")
    public ResponseEntity <SucursalResponseDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<SucursalResponseDTO> actualizar (@PathVariable Long id, @Valid @RequestBody SucursalRequestDTO request){
        return ResponseEntity.ok(sucursalService.actualizar(id, request));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<SucursalResponseDTO> eliminar (@PathVariable Long id){
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}