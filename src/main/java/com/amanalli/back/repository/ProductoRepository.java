package com.amanalli.back.repository;

import com.amanalli.back.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findByNombreProducto(String nombreProducto);

    @Query("SELECT c FROM Producto c WHERE c.estatusProducto = true")
    List<Producto> findProductosActivos();

    @Query("SELECT c FROM Producto c WHERE c.idProducto = :id AND c.estatusProducto = true")
    Optional<Producto> findByIdAndActivo(Long id);
}
