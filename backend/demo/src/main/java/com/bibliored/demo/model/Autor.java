package com.bibliored.demo.model;

import jakarta.persistence.*;

@Entity
@Table (name = "05_autores")
public class Autor {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Long idAutor;
    @Column(name = "nombre_autor", nullable = false, length =100)
    private String nombreAutor;
    @Column(name = "anno_nacimiento", nullable = false, length = 4)
    private String annoNacimiento;
    @Column(name = "anno_deceso", nullable = false, length = 4)
    private String annoDeceso;

    public Autor(Long idAutor, String nombreAutor, String annoNacimiento, String annoDeceso) {
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
        this.annoNacimiento = annoNacimiento;
        this.annoDeceso = annoDeceso;
    }

    public Autor() {
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getAnnoNacimiento() {
        return annoNacimiento;
    }

    public void setAnnoNacimiento(String annoNacimiento) {
        this.annoNacimiento = annoNacimiento;
    }

    public String getAnnoDeceso() {
        return annoDeceso;
    }

    public void setAnnoDeceso(String annoDeceso) {
        this.annoDeceso = annoDeceso;
    }
}