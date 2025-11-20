package com.amanalli.back.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "regiones")
public class Regiones {
    // Variables de instancia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    private Long idRegion;
    @Column(name = "nombre_region", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(50)")
    private String nombreRegion;
    @Column (name = "estatus_region", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean estatusRegion;
    /*
    // Cardinalidad Regiones -> Productos 1:N
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regiones")
    private List<Regiones> regiones = new ArrayList<>();
    */
    //Constructores
    public Regiones(Long idRegion, String nombreRegion, Boolean estatusRegion) {
        this.idRegion = idRegion;
        this.nombreRegion = nombreRegion;
        this.estatusRegion = estatusRegion;
    }

    public Regiones(){

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
        if (!(o instanceof Regiones regiones)) return false;
        return Objects.equals(idRegion, regiones.idRegion) && Objects.equals(nombreRegion, regiones.nombreRegion) && Objects.equals(estatusRegion, regiones.estatusRegion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRegion, nombreRegion, estatusRegion);
    }
}
