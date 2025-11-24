package com.amanalli.back.repository;

import com.amanalli.back.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Long> {
    Categorias findByNombreCategoria(String nombreCategoria);

    @Query("SELECT c FROM Categorias c WHERE c.estatusCategoria = true")
    List<Categorias> findCategoriasActivas();

    @Query("SELECT c FROM Categorias c WHERE c.idCategoria = :id AND c.estatusCategoria = true")
    Optional<Categorias> findByIdAndActivo(Long id);
}
