package com.amanalli.back.repository;

import com.amanalli.back.model.VentaPedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaPedidoRepository extends JpaRepository<VentaPedidos, Long> {
}
