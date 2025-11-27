package com.amanalli.back.controller;

import com.amanalli.back.exceptions.DetallePedidoNotFoundException;
import com.amanalli.back.model.DetallePedido;
import com.amanalli.back.service.DetallePedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/detalle-pedido")
public class DetallePedidoController {

    // === Inyección del Service ===
    private final DetallePedidoService detallePedidoService;

    public DetallePedidoController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    // === Obtener todos los detalles (GET) ===
    @GetMapping
    public List<DetallePedido> getDetallesPedido() {
        return detallePedidoService.getDetallesPedido();
    }

    // === Crear nuevo detalle de pedido (POST) ===
    @PostMapping("/nuevo-detalle")
    public ResponseEntity<DetallePedido> crearDetalle(@RequestBody DetallePedido detalle) {
        DetallePedido nuevoDetalle = detallePedidoService.createDetallePedido(detalle);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDetalle);
    }

    // === Obtener un detalle por ID (GET) ===
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> getDetallePedidoById(@PathVariable Long id) {
        try {
            DetallePedido detalle = detallePedidoService.getDetallePedidoById(id);
            return ResponseEntity.status(HttpStatus.OK).body(detalle);
        } catch (DetallePedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // === Actualizar un detalle de pedido (PUT) ===
    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> updateDetallePedido(
            @RequestBody DetallePedido detalle,
            @PathVariable Long id
    ) {
        try {
            DetallePedido detalleActualizado = detallePedidoService.updateDetallePedido(detalle, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(detalleActualizado);
        } catch (DetallePedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // === Eliminar un detalle de pedido (DELETE físico) ===
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDetallePedido(@PathVariable Long id) {
        try {
            detallePedidoService.deleteDetallePedidoById(id);
            return ResponseEntity.noContent().build();
        } catch (DetallePedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
