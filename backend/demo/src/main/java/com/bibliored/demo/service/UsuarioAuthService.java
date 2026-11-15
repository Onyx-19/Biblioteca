package com.bibliored.demo.service;

import com.bibliored.demo.dto.UsuarioAuthRequestDTO;
import com.bibliored.demo.dto.UsuarioAuthResponseDTO;

public interface UsuarioAuthService{
     UsuarioAuthResponseDTO login(UsuarioAuthRequestDTO request);
}
