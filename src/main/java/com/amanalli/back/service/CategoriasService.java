package com.amanalli.back.service;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.model.Categorias;
import com.amanalli.back.repository.CategoriasRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriasService {
    private final CategoriasRepository categoriasRepository;

    @Autowired
    public CategoriasService(CategoriasRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    public Categorias findByNombreCategoria(String nombreCategoria) {
        return categoriasRepository.findByNombreCategoria(nombreCategoria);
    }

    // Obtener la información de todos los usuarios (GET)
    public List<Categorias> getCategorias() {
        return categoriasRepository.findCategoriasActivas();
    }

    // Crear nueva categoria (POST)
    public Categorias createUser(Categorias categoria) {
        return categoriasRepository.save(categoria);
    }

    // Obtener los datos de una categoria (GET)
    public Categorias getCategoriasById(Long id) {
        Optional<Categorias> categorias = categoriasRepository.findByIdAndActivo(id);
        return categorias.orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    // Actualizar una categoria (PUT)
    public Categorias updateCategorias(Categorias categoria, Long id) {
        return categoriasRepository.findByIdAndActivo(id)
                .map(categorias -> {
                    categorias.setNombreCategoria(categoria.getNombreCategoria());
                    categorias.setDescripcionCategoria(categoria.getDescripcionCategoria());
                    categorias.setEstatusCategoria(categoria.getEstatusCategoria());
                    return categoriasRepository.save(categorias);
                })
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    // Eliminar un usuario (DELETE)
    @Transactional
    public void deleteCategoriasById(Long id) {
        if (!categoriasRepository.existsById(id)){
            throw new CategoriaNotFoundException(id);
        }
        Categorias categorias = categoriasRepository.getReferenceById(id);
        categorias.desactivarById();
    }

}
