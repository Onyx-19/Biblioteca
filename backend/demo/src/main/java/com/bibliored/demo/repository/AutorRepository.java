package com.bibliored.demo.repository;

import com.bibliored.demo.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository <Autor, Long> {
    Optional<Autor> findByNombreAutor(String nombreAutor);
    Optional<Autor> findById(Long id);
}