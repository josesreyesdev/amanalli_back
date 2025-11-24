package com.amanalli.back.service;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.exceptions.ProductoNotFoundException;
import com.amanalli.back.model.Categorias;
import com.amanalli.back.model.Productos;
import com.amanalli.back.model.Regiones;
import com.amanalli.back.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ProductosService {
    // === Inyección del Repository ===
    private final ProductosRepository productosRepository;

    @Autowired
    public ProductosService(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    // === Obtener la información de todas las categorias (GET) ===
    public List<Productos> getProductos() {
        return productosRepository.findProductosActivos();
    }

    // === Crear nueva categoria (POST) ===
    public Productos createProducto(Productos producto) {
        return productosRepository.save(producto);
    }

    public Productos findByNombreProducto(String nombreProducto) {
        return productosRepository.findByNombreProducto(nombreProducto);
    }

    // === Obtener los datos de una categoria por ID (GET) ===
    public Productos getProductosById(Long id) {
        Optional<Productos> productos = productosRepository.findByIdAndActivo(id);
        return productos.orElseThrow(() -> new ProductoNotFoundException(id));
    }

    // === Actualizar una categoria activa (PUT) ===
    public Productos updateProductos(Productos producto, Long id) {
        return productosRepository.findByIdAndActivo(id)
                .map(productos -> {
                    productos.setNombreProducto(producto.getNombreProducto());
                    productos.setDescripcionProducto(producto.getDescripcionProducto());
                    productos.
                    categorias.setNombreCategoria(categoria.getNombreCategoria());
                    categorias.setDescripcionCategoria(categoria.getDescripcionCategoria());
                    return categoriasRepository.save(categorias);
                })
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    // === Activar una categoria Estatus = True (PUT) ===
    public Categorias updateCategoriasInactivas (Long id) {
        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));

        categoria.activar();
        return categoriasRepository.save(categoria);
    }

    // === Eliminar/desactivar una categoria Estatus = False (DELETE) ===
    public void deleteCategoriasById(Long id) {
        if (!categoriasRepository.existsById(id)){
            throw new CategoriaNotFoundException(id);
        }
        Categorias categorias = categoriasRepository.getReferenceById(id);
        categorias.desactivarById();
    }
}
