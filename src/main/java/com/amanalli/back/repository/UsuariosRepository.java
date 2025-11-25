package com.amanalli.back.repository;

import com.amanalli.back.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    @Query("SELECT u FROM Usuarios u WHERE u.activo = true")
    List<Usuarios> findAllAndActivoTrue();

    @Query("SELECT u FROM Usuarios u WHERE u.idUsuario = :id AND u.activo = true")
    Optional<Usuarios> findByIdAndActivoTrue(Long id);

    Usuarios findByNombreCompleto(String nombreCompleto);

    Usuarios findByEmail(String email);

    Usuarios findByTelefono(String telefono);
}
