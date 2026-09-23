package com.bibliored.demo.model;

import jakarta.persistence.*;

@Entity
@Table (name = "06_categorias")
public class Categoria {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_categoria")
    private Long idCategoria;
    @Column (name = "nombre_categoria", nullable = false, length = 100)
    private String nombreCategoria;
    @Column (name = "codigo_estante", length = 20)
    private String codigoEstante;

    public Categoria(Long idCategoria, String nombreCategoria, String codigoEstante) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.codigoEstante = codigoEstante;
    }

    public Categoria(){
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getCodigoEstante() {
        return codigoEstante;
    }

    public void setCodigoEstante(String codigoEstante) {
        this.codigoEstante = codigoEstante;
    }
}