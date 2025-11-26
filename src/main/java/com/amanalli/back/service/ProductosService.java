package com.amanalli.back.service;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.exceptions.ProductoNotFoundException;
import com.amanalli.back.exceptions.RegionNotFoundException;
import com.amanalli.back.model.Categoria;
import com.amanalli.back.model.Producto;
import com.amanalli.back.model.Region;
import com.amanalli.back.repository.CategoriaRepository;
import com.amanalli.back.repository.ProductoRepository;
import com.amanalli.back.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosService {
    // === Inyección del Repository ===
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final RegionRepository regionRepository;

    @Autowired
    public ProductosService(ProductoRepository productoRepository,
                            CategoriaRepository categoriaRepository,
                            RegionRepository regionRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.regionRepository = regionRepository;
    }

    // === Obtener la información de todos los productos activos (GET) ===
    public List<Producto> getProductos() {
        return productoRepository.findProductosActivos();
    }

    // === Crear nuevo producto (POST) ===
    public Producto crearProducto(Producto producto) {
        // Validar que la categoria y region existan y estén activas
        Categoria categoriaValida = validarCategoriaActiva(
                producto.getCategorias().getIdCategoria()
        );
        Region regionValida = validarRegionActiva(
                producto.getRegiones().getIdRegion()
        );

        // Asignar las entidades validadas al producto
        producto.setCategorias(categoriaValida);
        producto.setRegiones(regionValida);
        return productoRepository.save(producto);
    }

    public Producto findByNombreProducto(String nombreProducto) {
        return productoRepository.findByNombreProducto(nombreProducto);
    }

    // === Obtener los datos de un producto por ID (GET) ===
    public Producto getProductosById(Long id) {
        Optional<Producto> productos = productoRepository.findByIdAndActivo(id);
        return productos.orElseThrow(() -> new ProductoNotFoundException(id));
    }

    // === Actualizar un producto activo (PUT) ===
    public Producto updateProductos(Producto producto, Long id) {
        return productoRepository.findByIdAndActivo(id)
                .map(productos -> {
                    // Validar que la categoria y region existan
                    Categoria categoriaValida = validarCategoriaActiva(
                            producto.getCategorias().getIdCategoria()
                    );
                    Region regionValida = validarRegionActiva(
                            producto.getRegiones().getIdRegion()
                    );

                    // Actualizar los campos
                    productos.setNombreProducto(producto.getNombreProducto());
                    productos.setDescripcionProducto(producto.getDescripcionProducto());
                    productos.setPrecio(producto.getPrecio());
                    productos.setImagen(producto.getImagen());
                    productos.setStock(producto.getStock());
                    productos.setCategorias(categoriaValida);
                    productos.setRegiones(regionValida);
                    return productoRepository.save(productos);
                })
                .orElseThrow(() -> new ProductoNotFoundException(id));
    }

    // === Activar un producto Estatus = True (PUT) ===
    public Producto updateProductosInactivas (Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        producto.activar();
        return productoRepository.save(producto);
    }

    // === Eliminar/desactivar un producto Estatus = False (DELETE) ===
    public void deleteCategoriasById(Long id) {
        if (!productoRepository.existsById(id)){
            throw new ProductoNotFoundException(id);
        }
        Producto producto = productoRepository.getReferenceById(id);
        producto.desactivarById();
    }

    // === Validar si la categoria y region ingresadas existen y tienen Estatus = True ===
    private Categoria validarCategoriaActiva(Long idCategoria) {
        return categoriaRepository.findByIdAndActivo(idCategoria)
                .orElseThrow(() -> new CategoriaNotFoundException(idCategoria));
    }

    private Region validarRegionActiva(Long idRegion) {
        return regionRepository.findByIdAndActivo(idRegion)
                .orElseThrow(() -> new RegionNotFoundException(idRegion));
    }
}
