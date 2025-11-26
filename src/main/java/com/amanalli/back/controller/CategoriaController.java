package com.amanalli.back.controller;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.model.Categoria;
import com.amanalli.back.service.CategoriaService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    // === Inyección del Service ===
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // === Obtener la información de todas las categorias (GET) ===
    @GetMapping
    public List<Categoria> getCategorias(){
        return categoriaService.getCategorias();
    }

    // === Crear nueva categoria (POST) ===
    @PostMapping("/nueva-categoria")
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria){
        Categoria findCategoria = categoriaService.findByNombreCategoria(categoria.getNombreCategoria());
        if (findCategoria != null){
            //409 conflict
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else {
            //201 created, hay que pasarle un body
            return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.createCategoria(categoria));
        }
    }

    // === Obtener los datos de una categoria por ID (GET) ===
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById (@PathVariable Long id){
        try {
            Categoria categoria = categoriaService.getCategoriasById(id);
            return ResponseEntity.status(HttpStatus.OK).body(categoria);
        }catch (CategoriaNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // === Actualizar una categoria activa (PUT) ===
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria (@RequestBody Categoria categoria, @PathVariable Long id){
        try {
            Categoria categorias = categoriaService.updateCategorias(categoria, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(categorias);
        } catch (CategoriaNotFoundException e){
            return  ResponseEntity.notFound().build();
        }
    }

    // === Activar una categoria Estatus = True (PUT) ===
    @Transactional
    @PutMapping("/activar/{id}")
    public ResponseEntity<Categoria> updateCategoriasInactivas (@PathVariable Long id) {
        try {
            Categoria categoria = categoriaService.updateCategoriasInactivas(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
        } catch (CategoriaNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // === Eliminar/desactivar una region Estatus = False (DELETE) ===
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoriasById (@PathVariable Long id){
        try {
            categoriaService.deleteCategoriasById(id);
            return ResponseEntity.noContent().build();
        } catch (CategoriaNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
