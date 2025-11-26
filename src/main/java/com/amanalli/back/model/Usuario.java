package com.amanalli.back.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    //Variables de instancia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(name = "nombre_completo", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
    private String nombreCompleto;
    @Column(name = "email", nullable = false, unique = true, length = 100, columnDefinition = "VARCHAR(100)")
    private String email;
    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;
    @Column(name = "telefono", nullable = false, unique = true,length = 20, columnDefinition = "VARCHAR(20)")
    private String telefono;
    @Column (name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private List<Rol> roles = new ArrayList<>();

    // Cardinalidad Usuarios -> VentaPedido 1:N
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<VentaPedido> ventaPedidos = new ArrayList<>();

    //Constructores
    public Usuario(Long idUsuario, String nombreCompleto, String email, String password, String telefono, List<Rol> roles) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.roles = roles;
    }
    public Usuario(){}

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority("ROL_" + rol.getNombre()))
                .toList();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(idUsuario, usuario.idUsuario) && Objects.equals(nombreCompleto, usuario.nombreCompleto) && Objects.equals(email, usuario.email) && Objects.equals(activo, usuario.activo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, nombreCompleto, email, activo);
    }

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }
}
