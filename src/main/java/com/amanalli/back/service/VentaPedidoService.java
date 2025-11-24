package com.amanalli.back.service;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.exceptions.VentaPedidosNotFoundException;
import com.amanalli.back.model.Categorias;
import com.amanalli.back.model.VentaPedidos;
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
    public List<VentaPedidos> getVentaPedidos(){
        return ventaPedidoRepository.findVentaPedidosActivas();
    }

    // === Crear nueva venta (POST) ===
    public VentaPedidos createVentaPedidos(VentaPedidos ventaPedidos) {
        return ventaPedidoRepository.save(ventaPedidos);
    }

    // === Obtener los datos de una venta por ID (GET) ===
    public VentaPedidos getVentaPedidosById (Long id) {
        Optional<VentaPedidos> ventaPedidos = ventaPedidoRepository.findByIdAndActivo(id);
        return ventaPedidos.orElseThrow(() -> new VentaPedidosNotFoundException(id));
    }

    // === Actualizar una venta activa (PUT) ===
    public VentaPedidos updateVentaPedidos(VentaPedidos ventaPedido, Long id) {
        return ventaPedidoRepository.findByIdAndActivo(id)
                .map(ventaPedidos -> {
                    ventaPedidos.setTotalVenta(ventaPedido.getTotalVenta());
                    ventaPedidos.setFechaPedido(ventaPedido.getFechaPedido());
                    return ventaPedidoRepository.save(ventaPedidos);
                })
                .orElseThrow(() -> new VentaPedidosNotFoundException(id));
    }

}
