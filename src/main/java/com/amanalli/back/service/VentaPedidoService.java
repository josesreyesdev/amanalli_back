package com.amanalli.back.service;

import com.amanalli.back.exceptions.VentaPedidosNotFoundException;
import com.amanalli.back.model.VentaPedido;
import com.amanalli.back.repository.VentaPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaPedidoService {

    // === Inyeccion del Repository ===
    private final VentaPedidoRepository ventaPedidoRepository;

    @Autowired
    public VentaPedidoService(VentaPedidoRepository ventaPedidoRepository) {
        this.ventaPedidoRepository = ventaPedidoRepository;
    }

    // === Obtener la informacion de todas las ventas (GET) ===
    public List<VentaPedido> getVentaPedidos() {
        return ventaPedidoRepository.findVentaPedidosActivas();
    }

    // === Crear nueva venta (POST) ===
    public VentaPedido createVentaPedidos(VentaPedido ventaPedido) {
        return ventaPedidoRepository.save(ventaPedido);
    }

    // === Obtener los datos de una venta por ID (GET) ===
    public VentaPedido getVentaPedidosById(Long id) {
        Optional<VentaPedido> ventaPedidos = ventaPedidoRepository.findByIdAndActivo(id);
        return ventaPedidos.orElseThrow(() -> new VentaPedidosNotFoundException(id));
    }

    // === Actualizar una venta activa (PUT) ===
    public VentaPedido updateVentaPedidos(VentaPedido ventaPedido, Long id) {
        return ventaPedidoRepository.findByIdAndActivo(id)
                .map(ventaPedidos -> {
                    ventaPedidos.setTotalVenta(ventaPedido.getTotalVenta());
                    ventaPedidos.setFechaPedido(ventaPedido.getFechaPedido());
                    return ventaPedidoRepository.save(ventaPedidos);
                })
                .orElseThrow(() -> new VentaPedidosNotFoundException(id));
    }

}
