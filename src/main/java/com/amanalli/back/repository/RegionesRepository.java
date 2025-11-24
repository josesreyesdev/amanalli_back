package com.amanalli.back.repository;

import com.amanalli.back.model.Categorias;
import com.amanalli.back.model.Regiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionesRepository extends JpaRepository<Regiones, Long> {
    Regiones findByNombreRegion(String nombreRegion);

    @Query("SELECT c FROM Regiones c WHERE c.estatusRegion = true")
    List<Regiones> findRegionesActivas();

    @Query("SELECT c FROM Regiones c WHERE c.idRegion = :id AND c.estatusRegion = true")
    Optional<Regiones> findByIdAndActivo(Long id);
}
