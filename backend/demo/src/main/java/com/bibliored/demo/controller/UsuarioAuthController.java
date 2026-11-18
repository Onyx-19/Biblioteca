package com.bibliored.demo.controller;

import com.bibliored.demo.dto.UsuarioAuthRequestDTO;
import com.bibliored.demo.dto.UsuarioAuthResponseDTO;
import com.bibliored.demo.dto.UsuarioResetPassRequestDTO;
import com.bibliored.demo.service.UsuarioAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class UsuarioAuthController {
    private final UsuarioAuthService usuarioAuthService;

    public UsuarioAuthController(UsuarioAuthService usuarioAuthService) {
        this.usuarioAuthService = usuarioAuthService;
    }

    @PostMapping
    public ResponseEntity<UsuarioAuthResponseDTO> login (@Valid @RequestBody UsuarioAuthRequestDTO request) {
        UsuarioAuthResponseDTO ingreso = usuarioAuthService.login(request);
        return ResponseEntity.ok(ingreso);
    }

    @PatchMapping("/recuperar")
    public ResponseEntity<Void> reset (@Valid @RequestBody UsuarioResetPassRequestDTO request) {
        usuarioAuthService.reset(request);
        return ResponseEntity.noContent().build();
    }
}
