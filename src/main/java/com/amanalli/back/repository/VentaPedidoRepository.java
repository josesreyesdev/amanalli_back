package com.amanalli.back.repository;

import com.amanalli.back.model.VentaPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaPedidoRepository extends JpaRepository<VentaPedido, Long> {

    @Query("SELECT vp FROM VentaPedido vp WHERE vp.estatusPedido = true")
    List<VentaPedido> findVentaPedidosActivas();

    @Query("SELECT vp FROM VentaPedido vp WHERE vp.idVenta = :id AND vp.estatusPedido = true")
    Optional<VentaPedido> findByIdAndActivo(Long id);
}
