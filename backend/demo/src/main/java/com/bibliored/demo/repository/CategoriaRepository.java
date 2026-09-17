package com.bibliored.demo.repository;

import com.bibliored.demo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findById(Long Id);
    Optional<Categoria> findByNombreCategoria(String nombreCategoria);
}