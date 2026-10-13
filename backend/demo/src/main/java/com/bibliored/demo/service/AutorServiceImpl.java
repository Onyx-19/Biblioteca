package com.bibliored.demo.service;

import com.bibliored.demo.component.ValidadorNegocio;
import com.bibliored.demo.dto.AutorRequestDTO;
import com.bibliored.demo.dto.AutorResponseDTO;
import com.bibliored.demo.model.Autor;
import com.bibliored.demo.repository.AutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorServiceImpl implements AutorService{

    private final AutorRepository autorRepository;
    private final ValidadorNegocio validadorNegocio;

    private AutorServiceImpl (AutorRepository autorRepository,
                              ValidadorNegocio validadorNegocio){
        this.autorRepository = autorRepository;
        this.validadorNegocio = validadorNegocio;
    }

    @Override
    public AutorResponseDTO crear(AutorRequestDTO request) {
        Autor autor = new Autor();
        String nombre = validadorNegocio.formatoNombre(request.nombreAutor());

        autor.setNombreAutor(nombre);
        autor.setAnnoNacimiento(request.annoNacimiento());
        autor.setAnnoDeceso(request.annoDeceso());

        Autor guardado = autorRepository.save(autor);
        return toResponseDTO(guardado);
    }

    @Override
    public List<AutorResponseDTO> listarTodos() {
        return autorRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public AutorResponseDTO obtenerPorID(Long id) {
        Autor autor = validacionId(id);
        return toResponseDTO(autor);
    }

    @Override
    public AutorResponseDTO actualizar(Long id, AutorRequestDTO request) {
        Autor autor = validacionId(id);
        String nombre = validadorNegocio.formatoNombre(request.nombreAutor());

        autor.setNombreAutor(nombre);
        autor.setAnnoNacimiento(request.annoNacimiento());
        autor.setAnnoDeceso(request.annoDeceso());

        Autor actualizado = autorRepository.save(autor);
        return toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Autor autor = validacionId(id);
        autorRepository.delete(autor);
    }

    private Autor validacionId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron autores con id: " + id));
    }

    private AutorResponseDTO toResponseDTO(Autor autor) {
        return new AutorResponseDTO(
            autor.getIdAutor(),
            autor.getNombreAutor(),
            autor.getAnnoNacimiento(),
            autor.getAnnoDeceso()
        );
    }
}
