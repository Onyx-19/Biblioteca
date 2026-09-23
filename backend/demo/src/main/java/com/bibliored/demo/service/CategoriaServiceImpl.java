package com.bibliored.demo.service;

import com.bibliored.demo.dto.CategoriaRequestDTO;
import com.bibliored.demo.dto.CategoriaResponseDTO;
import com.bibliored.demo.model.Categoria;
import com.bibliored.demo.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService{
    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaResponseDTO crear(CategoriaRequestDTO request) {
        Categoria categoria = new Categoria();

        categoria.setNombreCategoria(request.nombreCategoria());
        categoria.setCodigoEstante(request.codigoEstante());

        Categoria guardada = categoriaRepository.save(categoria);
        return toResponseDTO(guardada);
    }

    @Override
    public List<CategoriaResponseDTO> ListarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public CategoriaResponseDTO obtenerPorID(Long id) {
        Categoria categoria = validacionId(id);
        return toResponseDTO(categoria);
    }

    @Override
    public CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO request) {
        Categoria categoria = validacionId(id);

        categoria.setNombreCategoria(request.nombreCategoria());
        categoria.setCodigoEstante(request.codigoEstante());

        Categoria actualizada = categoriaRepository.save(categoria);
        return toResponseDTO(actualizada);
    }

    @Override
    public void eliminar(Long id) {
        Categoria categoria = validacionId(id);
        categoriaRepository.delete(categoria);
    }

    private Categoria validacionId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron categorias con id " + id));
    }

    private CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getIdCategoria(),
                categoria.getNombreCategoria(),
                categoria.getCodigoEstante()
        );
    }
}
