package com.bibliored.demo.repository;

import com.bibliored.demo.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findById(Long id);

    Optional<Libro> tituloLibro(String tituloLibro);

}
