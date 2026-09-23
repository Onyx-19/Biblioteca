package com.bibliored.demo.service;

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

    private AutorServiceImpl (AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }

    @Override
    public AutorResponseDTO crear(AutorRequestDTO request) {
        Autor autor = new Autor();

        autor.setNombreAutor(request.nombreAutor());
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

        autor.setNombreAutor(request.nombreAutor());
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
