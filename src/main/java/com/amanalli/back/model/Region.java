package com.amanalli.back.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "regiones")
public class Region {
    // Variables de instancia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    private Long idRegion;
    @Column(name = "nombre_region", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String nombreRegion;
    @Column (name = "estatus_region", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean estatusRegion = true;

    // Cardinalidad Regiones -> Productos 1:N
    @OneToMany(mappedBy = "regiones", cascade = CascadeType.ALL)
    private List<Producto> productos = new ArrayList<>();

    //Constructores
    public Region(Long idRegion, String nombreRegion, Boolean estatusRegion) {
        this.idRegion = idRegion;
        this.nombreRegion = nombreRegion;
        this.estatusRegion = estatusRegion;
    }

    public Region(){

    }

    //Getters y Setters
    public Long getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Long idRegion) {
        this.idRegion = idRegion;
    }

    public String getNombreRegion() {
        return nombreRegion;
    }

    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }

    public Boolean getEstatusRegion() {
        return estatusRegion;
    }

    public void setEstatusRegion(Boolean estatusRegion) {
        this.estatusRegion = estatusRegion;
    }

    //ToString
    @Override
    public String toString() {
        return "Regiones{" +
                "idRegion=" + idRegion +
                ", nombreRegion='" + nombreRegion + '\'' +
                ", estatusRegion=" + estatusRegion +
                '}';
    }

    // Equals y Hashcode
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Region regiones)) return false;
        return Objects.equals(idRegion, regiones.idRegion) && Objects.equals(nombreRegion, regiones.nombreRegion) && Objects.equals(estatusRegion, regiones.estatusRegion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRegion, nombreRegion, estatusRegion);
    }

    // === Activar una region Estatus = True (PUT) ===
    public Region activar() {
        this.estatusRegion = true;
        return this;
    }

    // Eliminar/desactivar una region Estatus = False (DELETE)
    public void desactivarById() {
        this.estatusRegion = false;
    }
}
