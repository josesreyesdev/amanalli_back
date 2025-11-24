package com.amanalli.back.repository;

import com.amanalli.back.model.Categorias;
import com.amanalli.back.model.VentaPedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaPedidoRepository extends JpaRepository<VentaPedidos, Long> {

    @Query("SELECT vp FROM venta_pedidos vp WHERE vp.estatus_pedido = true")
    List<VentaPedidos> findVentaPedidosActivas();

    @Query("SELECT vp FROM venta-pedidos vp WHERE vp.id_venta = :id_venta AND vp.estatus_pedido = true")
    Optional<VentaPedidos> findByIdAndActivo(Long id);
}
