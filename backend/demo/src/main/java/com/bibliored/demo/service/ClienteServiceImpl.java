package com.bibliored.demo.service;

import com.bibliored.demo.dto.ClienteRequestDTO;
import com.bibliored.demo.dto.ClienteResponseDTO;
import com.bibliored.demo.model.Cliente;
import com.bibliored.demo.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository clienteRepository;

    private ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteResponseDTO crear(ClienteRequestDTO request) {
        Cliente cliente = new Cliente();

        cliente.setIdentificacionCliente(request.identificacionCliente());
        cliente.setNombreCliente(request.nombreCliente());
        cliente.setDireccionCliente(request.direccionCliente());
        cliente.setCorreoCliente(request.correoCliente());
        cliente.setTelefonoCliente(request.telefonoCliente());

        Cliente guardado = clienteRepository.save(cliente);
        return toResponseDTO(guardado);
    }

    @Override
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public ClienteResponseDTO obtenerPorId(Long id) {
        Cliente cliente = validacionId(id);
        return toResponseDTO(cliente);
    }

    @Override
    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO request) {
        Cliente cliente = validacionId(id);

        cliente.setIdentificacionCliente(request.identificacionCliente());
        cliente.setNombreCliente(request.nombreCliente());
        cliente.setDireccionCliente(request.direccionCliente());
        cliente.setCorreoCliente(request.correoCliente());
        cliente.setTelefonoCliente(request.telefonoCliente());

        Cliente actualizado = clienteRepository.save(cliente);
        return toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Cliente cliente = validacionId(id);
        clienteRepository.delete(cliente);
    }

    private Cliente validacionId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado cliente con id: " + id));
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getIdCliente(),
                cliente.getIdentificacionCliente(),
                cliente.getNombreCliente(),
                cliente.getDireccionCliente(),
                cliente.getCorreoCliente(),
                cliente.getTelefonoCliente()
        );
    }
}
