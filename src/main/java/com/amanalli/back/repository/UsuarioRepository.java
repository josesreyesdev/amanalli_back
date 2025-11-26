package com.amanalli.back.repository;

import com.amanalli.back.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.activo = true")
    List<Usuario> findAllAndActivoTrue();

    @Query("SELECT u FROM Usuario u WHERE u.idUsuario = :id AND u.activo = true")
    Optional<Usuario> findByIdAndActivoTrue(Long id);

    Usuario findByNombreCompleto(String nombreCompleto);

    Usuario findByEmail(String email);

    Usuario findByTelefono(String telefono);
}
