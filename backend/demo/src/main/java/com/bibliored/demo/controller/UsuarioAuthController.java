package com.bibliored.demo.controller;

import com.bibliored.demo.dto.UsuarioAuthRequestDTO;
import com.bibliored.demo.dto.UsuarioAuthResponseDTO;
import com.bibliored.demo.service.UsuarioAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
