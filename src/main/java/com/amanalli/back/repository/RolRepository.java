package com.amanalli.back.repository;

import com.amanalli.back.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolRepository extends JpaRepository<Rol, Long> {
    @Query("SELECT r FROM Rol r WHERE r.nombre = :nombre")
    Rol findByNombre(String nombre);
}
