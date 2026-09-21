package com.bibliored.demo.service;

import com.bibliored.demo.dto.LibroRequestDTO;
import com.bibliored.demo.dto.LibroResponseDTO;
import com.bibliored.demo.model.Autor;
import com.bibliored.demo.model.Categoria;
import com.bibliored.demo.model.Libro;
import com.bibliored.demo.model.Sucursal;
import com.bibliored.demo.repository.AutorRepository;
import com.bibliored.demo.repository.CategoriaRepository;
import com.bibliored.demo.repository.LibroRepository;
import com.bibliored.demo.repository.SucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServiceImpl implements LibroService {
    private LibroRepository libroRespository;
    private AutorRepository autorRepository;
    private CategoriaRepository categoriaRepository;
    private SucursalRepository sucursalRepository;

    public LibroServiceImpl(LibroRepository libroRepository,
                            AutorRepository autorRepository,
                            CategoriaRepository categoriaRepository,
                            SucursalRepository sucursalRepository) {
        this.libroRespository = libroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    public LibroResponseDTO crear(LibroRequestDTO request) {
        Autor autor = buscarAutor(request.idAutor());
        Categoria categoria = buscarCategoria(request.idCategoria());
        Sucursal sucursal = buscarSucursal(request.idSucursal());
        Libro libro = new Libro();

        libro.setTituloLibro(request.tituloLibro());
        libro.setEditorial(request.editorial());
        libro.setIsbn(request.isbn());
        libro.setAnnoPublicacion(request.annoPublicacion());
        libro.setAutor(autor);
        libro.setCategoria(categoria);
        libro.setSucursal(sucursal);

        Libro guardado = libroRespository.save(libro);
        return toRespondeDTO(guardado);
    }

    @Override
    public List<LibroResponseDTO> listarTodos() {
        return libroRespository.findAll()
                .stream()
                .map(this::toRespondeDTO)
                .toList();
    }

    @Override
    public LibroResponseDTO obtenerPorId(Long id) {
        Libro libro = validacionPorId(id);
        return toRespondeDTO(libro);
    }

    @Override
    public LibroResponseDTO actualizar(Long id, LibroRequestDTO request) {
        Autor autor = buscarAutor(request.idAutor());
        Categoria categoria = buscarCategoria(request.idCategoria());
        Sucursal sucursal = buscarSucursal(request.idSucursal());
        Libro libro = validacionPorId(id);

        libro.setTituloLibro(request.tituloLibro());
        libro.setEditorial(request.editorial());
        libro.setIsbn(request.isbn());
        libro.setAnnoPublicacion(request.annoPublicacion());
        libro.setAutor(autor);
        libro.setCategoria(categoria);
        libro.setSucursal(sucursal);

        Libro actualizado = libroRespository.save(libro);
        return toRespondeDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Libro libro = validacionPorId(id);
        libroRespository.delete(libro);
    }

    private Libro validacionPorId(Long id) {
        return libroRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado un libro con id: " + id));
    }

    private Autor buscarAutor(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado un autor con id: " + id));
    }

    private Categoria buscarCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado una categoria con id: " + id));
    }

    private Sucursal buscarSucursal(Long id){
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado una sucursal con id: " + id));
    }

    private LibroResponseDTO toRespondeDTO(Libro libro) {
        return new LibroResponseDTO (
                libro.getIdLibro(),
                libro.getTituloLibro(),
                libro.getEditorial(),
                libro.getIsbn(),
                libro.getAnnoPublicacion(),
                libro.getStock(),
                libro.getAutor().getIdAutor(),
                libro.getAutor().getNombreAutor(),
                libro.getCategoria().getIdCategoria(),
                libro.getCategoria().getNombreCategoria(),
                libro.getSucursal().getIdSucursal(),
                libro.getSucursal().getNombreSucursal()
        );
    }
}
