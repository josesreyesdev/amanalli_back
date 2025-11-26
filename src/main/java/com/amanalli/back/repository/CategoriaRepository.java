package com.amanalli.back.repository;

import com.amanalli.back.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findByNombreCategoria(String nombreCategoria);

    @Query("SELECT c FROM Categoria c WHERE c.estatusCategoria = true")
    List<Categoria> findCategoriasActivas();

    @Query("SELECT c FROM Categoria c WHERE c.idCategoria = :id AND c.estatusCategoria = true")
    Optional<Categoria> findByIdAndActivo(Long id);
}
