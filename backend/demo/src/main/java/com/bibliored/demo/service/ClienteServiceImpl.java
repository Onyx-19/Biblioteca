package com.bibliored.demo.service;

import com.bibliored.demo.component.ValidadorNegocio;
import com.bibliored.demo.dto.ClienteRequestDTO;
import com.bibliored.demo.dto.ClienteResponseDTO;
import com.bibliored.demo.model.Cliente;
import com.bibliored.demo.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ValidadorNegocio validadorNegocio;

    private ClienteServiceImpl(ClienteRepository clienteRepository,
                               ValidadorNegocio validadorNegocio) {
        this.clienteRepository = clienteRepository;
        this.validadorNegocio = validadorNegocio;
    }

    @Override
    public ClienteResponseDTO crear(ClienteRequestDTO request) {
        Cliente cliente = new Cliente();
        String identificacion = request.identificacionCliente();
        String nombre = validadorNegocio.formatoNombre(request.nombreCliente());
        String correo = validadorNegocio.formatoCorreo(request.correoCliente());

        validadorNegocio.validarIdentificacionDuplicadoCliente(identificacion, null);
        validadorNegocio.validarCorreoDuplicadoCliente(correo, null);

        cliente.setIdentificacionCliente(identificacion);
        cliente.setNombreCliente(nombre);
        cliente.setCorreoCliente(correo);
        cliente.setDireccionCliente(request.direccionCliente());
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
        String identificacion = request.identificacionCliente();
        String nombre = validadorNegocio.formatoNombre(request.nombreCliente());
        String correo = validadorNegocio.formatoCorreo(request.correoCliente());

        validadorNegocio.validarIdentificacionDuplicadoCliente(identificacion, id);
        validadorNegocio.validarIdentificacionDuplicadoCliente(correo, id);

        cliente.setIdentificacionCliente(identificacion);
        cliente.setNombreCliente(nombre);
        cliente.setCorreoCliente(correo);
        cliente.setDireccionCliente(request.direccionCliente());
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
