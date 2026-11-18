package com.bibliored.demo.service;

import com.bibliored.demo.dto.UsuarioAuthRequestDTO;
import com.bibliored.demo.dto.UsuarioAuthResponseDTO;
import com.bibliored.demo.dto.UsuarioResetPassRequestDTO;

public interface UsuarioAuthService{
     UsuarioAuthResponseDTO login(UsuarioAuthRequestDTO request);

    void reset(UsuarioResetPassRequestDTO request);
}
