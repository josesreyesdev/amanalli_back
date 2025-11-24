package com.amanalli.back.controller;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.model.Categorias;
import com.amanalli.back.service.CategoriasService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {
    // === Inyección del Service ===
    private final CategoriasService categoriasService;

    public CategoriasController(CategoriasService categoriasService) {
        this.categoriasService = categoriasService;
    }

    // === Obtener la información de todas las categorias (GET) ===
    @GetMapping
    public List<Categorias> getCategorias(){
        return categoriasService.getCategorias();
    }

    // === Crear nueva categoria (POST) ===
    @PostMapping("/nueva-categoria")
    public ResponseEntity<Categorias> crearCategoria(@RequestBody Categorias categoria){
        Categorias findCategoria = categoriasService.findByNombreCategoria(categoria.getNombreCategoria());
        if (findCategoria != null){
            //409 conflict
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else {
            //201 created, hay que pasarle un body
            return ResponseEntity.status(HttpStatus.CREATED).body(categoriasService.createCategoria(categoria));
        }
    }

    // === Obtener los datos de una categoria por ID (GET) ===
    @GetMapping("/{id}")
    public ResponseEntity<Categorias> getCategoriaById (@PathVariable Long id){
        try {
            Categorias categorias = categoriasService.getCategoriasById(id);
            return ResponseEntity.status(HttpStatus.OK).body(categorias);
        }catch (CategoriaNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // === Actualizar una categoria activa (PUT) ===
    @PutMapping("/{id}")
    public ResponseEntity<Categorias> updateCategoria (@RequestBody Categorias categoria, @PathVariable Long id){
        try {
            Categorias categorias = categoriasService.updateCategorias(categoria, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(categorias);
        } catch (CategoriaNotFoundException e){
            return  ResponseEntity.notFound().build();
        }
    }

    // === Activar una categoria Estatus = True (PUT) ===
    @Transactional
    @PutMapping("/activar/{id}")
    public ResponseEntity<Categorias> updateCategoriasInactivas (@PathVariable Long id) {
        try {
            Categorias categorias = categoriasService.updateCategoriasInactivas(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(categorias);
        } catch (CategoriaNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // === Eliminar/desactivar una region Estatus = False (DELETE) ===
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoriasById (@PathVariable Long id){
        try {
            categoriasService.deleteCategoriasById(id);
            return ResponseEntity.noContent().build();
        } catch (CategoriaNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
