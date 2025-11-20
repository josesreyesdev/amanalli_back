package com.amanalli.back.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categorias")
public class Categorias {
    // Variables de instancia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;
    @Column(name = "nombre_categoria", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String nombreCategoria;
    @Column (name = "descripcion_categoria", nullable = false, columnDefinition = "TEXT")
    private String descripcionCategoria;
    @Column (name = "estatus_categoria", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean estatusCategoria;
    /*
    // Cardinalidad Categorias -> Productos 1:N
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categorias")
    private List<Productos> productos = new ArrayList<>();
    */
    //Constructores
    public Categorias(Long idCategoria, String nombreCategoria, String descripcionCategoria, Boolean estatusCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
        this.estatusCategoria = estatusCategoria;
    }

    public Categorias(){

    }

    //Getters y Setters
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

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    public Boolean getEstatusCategoria() {
        return estatusCategoria;
    }

    public void setEstatusCategoria(Boolean estatusCategoria) {
        this.estatusCategoria = estatusCategoria;
    }

    // ToString
    @Override
    public String toString() {
        return "Categorias{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                ", descripcionCategoria='" + descripcionCategoria + '\'' +
                ", estatusCategoria=" + estatusCategoria +
                '}';
    }

    // Equals y Hashcode
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Categorias that)) return false;
        return Objects.equals(idCategoria, that.idCategoria) && Objects.equals(nombreCategoria, that.nombreCategoria) && Objects.equals(descripcionCategoria, that.descripcionCategoria) && Objects.equals(estatusCategoria, that.estatusCategoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategoria, nombreCategoria, descripcionCategoria, estatusCategoria);
    }
}
