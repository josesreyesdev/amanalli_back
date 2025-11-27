package com.amanalli.back.controller;

import com.amanalli.back.exceptions.VentaPedidosNotFoundException;
import com.amanalli.back.model.VentaPedido;
import com.amanalli.back.service.VentaPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/venta-pedidos")
public class VentaPedidosController {

    // === Inyeccion del Service ===
    private final VentaPedidoService ventaPedidoService;


    public VentaPedidosController(VentaPedidoService ventaPedidoService) {
        this.ventaPedidoService = ventaPedidoService;
    }

    // === Obtener la informacion de todos los pedidos (GET) ===
    @GetMapping
    public List<VentaPedido> getVentaPedidos() {
        return ventaPedidoService.getVentaPedidos();
    }

    // === Creare nuevo pedido (POST) ===
    @PostMapping("/nuevo-pedido")
    public ResponseEntity<VentaPedido> crearVentaPedido(@RequestBody VentaPedido ventaPedido) {
        VentaPedido findVentaPedido = ventaPedidoService.getVentaPedidosById(ventaPedido.getIdVenta());
        if (findVentaPedido != null) {
            //409 conflict
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            //201 created
            return ResponseEntity.status(HttpStatus.CREATED).body(ventaPedidoService.createVentaPedidos(ventaPedido));
        }
    }

    // === Obtener los datos de un pedido por ID (GET) ===
    @GetMapping("/{id}")
    public ResponseEntity<VentaPedido> getVentaPedidosById (@PathVariable Long id) {
        try {
            VentaPedido ventaPedido = ventaPedidoService.getVentaPedidosById(id);
            return ResponseEntity.status(HttpStatus.OK).body(ventaPedido);
        } catch (VentaPedidosNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
