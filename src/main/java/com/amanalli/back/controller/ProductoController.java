package com.amanalli.back.controller;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.exceptions.ProductoNotFoundException;
import com.amanalli.back.exceptions.RegionNotFoundException;
import com.amanalli.back.model.Producto;
import com.amanalli.back.service.ProductosService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductoController {
    // === Inyección del Service ===
    private final ProductosService productosService;

    public ProductoController(ProductosService productosService) {
        this.productosService = productosService;
    }

    // === Obtener la información de todos los productos activos (GET) ===
    @GetMapping
    public List<Producto> getProductos() {
        return productosService.getProductos();
    }

    @SecurityRequirement(name = "bearer-key")
    // === Crear nuevo producto (POST) ===
    @PostMapping("/nuevo-producto")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto){
        try {
            // Validar si ya existe el producto por nombre
            Producto findProducto = productosService.findByNombreProducto(producto.getNombreProducto());
            if (findProducto != null){
                // 409 Conflict
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            // Válida categoría y región y crea producto
            Producto creado = productosService.crearProducto(producto);
            // 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (CategoriaNotFoundException | RegionNotFoundException e) {
            // 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @SecurityRequirement(name = "bearer-key")
    // === Obtener los datos de un producto por ID (GET) ===
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        try {
            Producto producto = productosService.getProductosById(id);
            return ResponseEntity.status(HttpStatus.OK).body(producto);
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @SecurityRequirement(name = "bearer-key")
    // === Actualizar un producto activo (PUT) ===
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto, @PathVariable Long id) {
        try {
            Producto productos = productosService.updateProductos(producto, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(productos);
        } catch (ProductoNotFoundException e) {
            // 404 Not Found
            return ResponseEntity.notFound().build();
        } catch (CategoriaNotFoundException | RegionNotFoundException e) {
            // 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // === Activar un producto Estatus = True (PUT) ===
    @Transactional
    @PutMapping("/activar/{id}")
    public ResponseEntity<Producto> activarProducto(@PathVariable Long id) {
        try {
            Producto producto = productosService.updateProductosInactivas(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(producto);
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // === Eliminar/desactivar un producto Estatus = False (DELETE) ===
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductoById(@PathVariable Long id) {
        try {
            productosService.deleteCategoriasById(id);
            return ResponseEntity.noContent().build();
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
