package com.amanalli.back.service;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.model.Categoria;
import com.amanalli.back.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    // === Inyección del Repository ===
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // === Obtener la información de todas las categorias (GET) ===
    public List<Categoria> getCategorias() {
        return categoriaRepository.findCategoriasActivas();
    }

    // === Crear nueva categoria (POST) ===
    public Categoria createCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria findByNombreCategoria(String nombreCategoria) {
        return categoriaRepository.findByNombreCategoria(nombreCategoria);
    }

    // === Obtener los datos de una categoria por ID (GET) ===
    public Categoria getCategoriasById(Long id) {
        Optional<Categoria> categorias = categoriaRepository.findByIdAndActivo(id);
        return categorias.orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    // === Actualizar una categoria activa (PUT) ===
    public Categoria updateCategorias(Categoria categoria, Long id) {
        return categoriaRepository.findByIdAndActivo(id)
                .map(categorias -> {
                    categorias.setNombreCategoria(categoria.getNombreCategoria());
                    categorias.setDescripcionCategoria(categoria.getDescripcionCategoria());
                    return categoriaRepository.save(categorias);
                })
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    // === Activar una categoria Estatus = True (PUT) ===
    public Categoria updateCategoriasInactivas (Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));

        categoria.activar();
        return categoriaRepository.save(categoria);
    }

    // === Eliminar/desactivar una categoria Estatus = False (DELETE) ===
    public void deleteCategoriasById(Long id) {
        if (!categoriaRepository.existsById(id)){
            throw new CategoriaNotFoundException(id);
        }
        Categoria categoria = categoriaRepository.getReferenceById(id);
        categoria.desactivarById();
    }
}
