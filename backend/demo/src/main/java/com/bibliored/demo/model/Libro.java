package com.bibliored.demo.model;

import jakarta.persistence.*;

@Entity
@Table (name = "07_libros")
public class Libro {
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    @Column (name = "id_libro")
    private Long idLibro;
    @Column (name = "titulo_libro",nullable = false, length = 100)
    private String tituloLibro;
    @Column (name = "editorial", length = 100)
    private String editorial;
    @Column (name = "isbn", length = 15)
    private String isbn;
    @Column (name = "anno_publicacion")
    private Integer annoPublicacion;
    @ManyToOne
    @JoinColumn (name = "id_autor")
    private Autor autor;
    @ManyToOne
    @JoinColumn (name = "id_categoria")
    private Categoria categoria;

    public Libro(Long idLibro, String tituloLibro, String editorial, String isbn, Integer annoPublicacion, Autor autor, Proveedor proveedor, Categoria categoria) {
        this.idLibro = idLibro;
        this.tituloLibro = tituloLibro;
        this.editorial = editorial;
        this.isbn = isbn;
        this.annoPublicacion = annoPublicacion;
        this.autor = autor;
        this.categoria = categoria;
    }

    public Libro(){
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnnoPublicacion() {
        return annoPublicacion;
    }

    public void setAnnoPublicacion(Integer annoPublicacion) {
        this.annoPublicacion = annoPublicacion;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
};
