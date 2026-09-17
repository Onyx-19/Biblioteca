package com.bibliored.demo.controller;

import com.bibliored.demo.dto.ClienteRequestDTO;
import com.bibliored.demo.dto.ClienteResponseDTO;
import com.bibliored.demo.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clientes")
public class ClienteController {
    private ClienteService clienteService;

    public ClienteController (ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crear (@Valid @RequestBody ClienteRequestDTO request){
        ClienteResponseDTO creacion = clienteService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creacion);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos(){
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity <ClienteResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO request){
        return ResponseEntity.ok(clienteService.actualizar(id, request));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity <ClienteResponseDTO> eliminar (@PathVariable Long id){
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
