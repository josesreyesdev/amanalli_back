package com.amanalli.back.service;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.model.Categorias;
import com.amanalli.back.repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriasService {
    // === Inyección del Repository ===
    private final CategoriasRepository categoriasRepository;

    @Autowired
    public CategoriasService(CategoriasRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    // === Obtener la información de todas las categorias (GET) ===
    public List<Categorias> getCategorias() {
        return categoriasRepository.findCategoriasActivas();
    }

    // === Crear nueva categoria (POST) ===
    public Categorias createCategoria(Categorias categoria) {
        return categoriasRepository.save(categoria);
    }

    public Categorias findByNombreCategoria(String nombreCategoria) {
        return categoriasRepository.findByNombreCategoria(nombreCategoria);
    }

    // === Obtener los datos de una categoria por ID (GET) ===
    public Categorias getCategoriasById(Long id) {
        Optional<Categorias> categorias = categoriasRepository.findByIdAndActivo(id);
        return categorias.orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    // === Actualizar una categoria activa (PUT) ===
    public Categorias updateCategorias(Categorias categoria, Long id) {
        return categoriasRepository.findByIdAndActivo(id)
                .map(categorias -> {
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
