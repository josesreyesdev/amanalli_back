package com.amanalli.back.service;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.exceptions.ProductoNotFoundException;
import com.amanalli.back.exceptions.RegionNotFoundException;
import com.amanalli.back.model.Categorias;
import com.amanalli.back.model.Productos;
import com.amanalli.back.model.Regiones;
import com.amanalli.back.repository.CategoriasRepository;
import com.amanalli.back.repository.ProductosRepository;
import com.amanalli.back.repository.RegionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosService {
    // === Inyección del Repository ===
    private final ProductosRepository productosRepository;
    private final CategoriasRepository categoriasRepository;
    private final RegionesRepository regionesRepository;

    @Autowired
    public ProductosService(ProductosRepository productosRepository,
                            CategoriasRepository categoriasRepository,
                            RegionesRepository regionesRepository) {
        this.productosRepository = productosRepository;
        this.categoriasRepository = categoriasRepository;
        this.regionesRepository = regionesRepository;
    }

    // === Obtener la información de todos los productos activos (GET) ===
    public List<Productos> getProductos() {
        return productosRepository.findProductosActivos();
    }

    // === Crear nuevo producto (POST) ===
    public Productos crearProducto(Productos producto) {
        // Validar que la categoria y region existan y estén activas
        Categorias categoriaValida = validarCategoriaActiva(
                producto.getCategorias().getIdCategoria()
        );
        Regiones regionValida = validarRegionActiva(
                producto.getRegiones().getIdRegion()
        );

        // Asignar las entidades validadas al producto
        producto.setCategorias(categoriaValida);
        producto.setRegiones(regionValida);
        return productosRepository.save(producto);
    }

    public Productos findByNombreProducto(String nombreProducto) {
        return productosRepository.findByNombreProducto(nombreProducto);
    }

    // === Obtener los datos de un producto por ID (GET) ===
    public Productos getProductosById(Long id) {
        Optional<Productos> productos = productosRepository.findByIdAndActivo(id);
        return productos.orElseThrow(() -> new ProductoNotFoundException(id));
    }

    // === Actualizar un producto activo (PUT) ===
    public Productos updateProductos(Productos producto, Long id) {
        return productosRepository.findByIdAndActivo(id)
                .map(productos -> {
                    // Validar que la categoria y region existan
                    Categorias categoriaValida = validarCategoriaActiva(
                            producto.getCategorias().getIdCategoria()
                    );
                    Regiones regionValida = validarRegionActiva(
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
                    return productosRepository.save(productos);
                })
                .orElseThrow(() -> new ProductoNotFoundException(id));
    }

    // === Activar un producto Estatus = True (PUT) ===
    public Productos updateProductosInactivas (Long id) {
        Productos producto = productosRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        producto.activar();
        return productosRepository.save(producto);
    }

    // === Eliminar/desactivar un producto Estatus = False (DELETE) ===
    public void deleteCategoriasById(Long id) {
        if (!productosRepository.existsById(id)){
            throw new ProductoNotFoundException(id);
        }
        Productos productos = productosRepository.getReferenceById(id);
        productos.desactivarById();
    }

    // === Validar si la categoria y region ingresadas existen y tienen Estatus = True ===
    private Categorias validarCategoriaActiva(Long idCategoria) {
        return categoriasRepository.findByIdAndActivo(idCategoria)
                .orElseThrow(() -> new CategoriaNotFoundException(idCategoria));
    }

    private Regiones validarRegionActiva(Long idRegion) {
        return regionesRepository.findByIdAndActivo(idRegion)
                .orElseThrow(() -> new RegionNotFoundException(idRegion));
    }
}
