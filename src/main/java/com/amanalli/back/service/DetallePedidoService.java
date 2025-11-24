package com.amanalli.back.service;

import com.amanalli.back.exceptions.DetallePedidoNotFoundException;
import com.amanalli.back.model.DetallePedido;
import com.amanalli.back.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoService {

    // === Inyección del Repository ===
    private final DetallePedidoRepository detallePedidoRepository;

    @Autowired
    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    // === Obtener la información de todos los DetallesPedido (GET) ===
    public List<DetallePedido> getDetallesPedido() {
        return detallePedidoRepository.findAll();
    }

    // === Crear un nuevo DetallePedido (POST) ===
    public DetallePedido createDetallePedido(DetallePedido detalle) {
        return detallePedidoRepository.save(detalle);
    }

    // === Obtener un DetallePedido por ID (GET) ===
    public DetallePedido getDetallePedidoById(Long id) {
        Optional<DetallePedido> detalle = detallePedidoRepository.findById(id);
        return detalle.orElseThrow(() -> new DetallePedidoNotFoundException(id));
    }

    // === Actualizar un DetallePedido (PUT) ===
    public DetallePedido updateDetallePedido(DetallePedido detalle, Long id) {
        return detallePedidoRepository.findById(id)
                .map(detalleExistente -> {
                    detalleExistente.setCantidad(detalle.getCantidad());
                    detalleExistente.setImporte(detalle.getImporte());
                    detalleExistente.setProductos(detalle.getProductos());
                    detalleExistente.setVentaPedidos(detalle.getVentaPedidos());
                    return detallePedidoRepository.save(detalleExistente);
                })
                .orElseThrow(() -> new DetallePedidoNotFoundException(id));
    }

    // === Eliminar un DetallePedido (DELETE) ===
    public void deleteDetallePedidoById(Long id) {
        if (!detallePedidoRepository.existsById(id)) {
            throw new DetallePedidoNotFoundException(id);
        }
        detallePedidoRepository.deleteById(id);
    }
}
