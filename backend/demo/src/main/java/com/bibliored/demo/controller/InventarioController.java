package com.bibliored.demo.controller;

import com.bibliored.demo.dto.InventarioRequestDTO;
import com.bibliored.demo.dto.InventarioResponseDTO;
import com.bibliored.demo.enums.MotivoMovimiento;
import com.bibliored.demo.enums.MovimientoInventario;
import com.bibliored.demo.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @GetMapping ("/tipos")
    public ResponseEntity<List<String>> tipos(){
        List<String> tipos = Arrays.stream(MovimientoInventario.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping ("/motivo")
    public ResponseEntity<List<String>> motivo(){
        List<String> motivo = Arrays.stream(MotivoMovimiento.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(motivo);
    }
}
