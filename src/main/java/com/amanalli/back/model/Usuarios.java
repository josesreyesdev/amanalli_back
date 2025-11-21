package com.amanalli.back.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuarios {
    //Variables de instancia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(name = "nombre_completo", nullable = false, unique = true, length = 100, columnDefinition = "VARCHAR(100)")
    private String nombreCompleto;
    @Column(name = "email", nullable = false, unique = true, length = 100, columnDefinition = "VARCHAR(100)")
    private String email;
    @Column(name = "password", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    private String password;
    @Column(name = "telefono", nullable = false, unique = true,length = 20, columnDefinition = "VARCHAR(20)")
    private String telefono;
    @Column (name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo;

    // Cardinalidad Usuarios -> VentaPedido 1:N
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private List<VentaPedidos> ventaPedidos = new ArrayList<>();

    //Constructores
    public Usuarios(Long idUsuario, String nombreCompleto, String email, String password, Boolean activo) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.password = password;
        this.activo = activo;
    }
    public Usuarios(){

    }

    //Getters y Setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    //ToString
    @Override
    public String toString() {
        return "Usuarios{" +
                "idUsuario=" + idUsuario +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", email='" + email + '\'' +
                ", activo=" + activo +
                '}';
    }

    //Equals y Hashcode
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuarios usuarios)) return false;
        return Objects.equals(idUsuario, usuarios.idUsuario) && Objects.equals(nombreCompleto, usuarios.nombreCompleto) && Objects.equals(email, usuarios.email) && Objects.equals(activo, usuarios.activo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, nombreCompleto, email, activo);
    }
}
