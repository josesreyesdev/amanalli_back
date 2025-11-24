package com.amanalli.back.repository;

import com.amanalli.back.model.Productos;
import com.amanalli.back.model.Regiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {
    Productos findByNombreProducto(String nombreProducto);

    @Query("SELECT c FROM Productos c WHERE c.estatusProducto = true")
    List<Productos> findProductosActivos();

    @Query("SELECT c FROM Productos c WHERE c.idProducto = :id AND c.estatusProducto = true")
    Optional<Productos> findByIdAndActivo(Long id);
}
