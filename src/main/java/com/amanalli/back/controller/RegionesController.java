package com.amanalli.back.controller;

import com.amanalli.back.exceptions.RegionNotFoundException;
import com.amanalli.back.model.Categorias;
import com.amanalli.back.model.Regiones;
import com.amanalli.back.service.RegionesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regiones")
public class RegionesController {
    private final RegionesService regionesService;

    @Autowired
    public RegionesController(RegionesService regionesService) {
        this.regionesService = regionesService;
    }

    // Obtener la información de todos los usuarios (GET)
    @GetMapping
    public List<Regiones> getCategorias(){
        return regionesService.getCategorias();
    }

    // Crear nueva region
    @PostMapping("/nueva-region")
    public ResponseEntity<Regiones> crearRegion (@RequestBody Regiones regiones){
        Regiones findRegion = regionesService.findByNombreRegion(regiones.getNombreRegion());
        if (findRegion != null){
            // 409 conflict
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            //201 created
            return ResponseEntity.status(HttpStatus.CREATED).body(regionesService.createRegion(regiones));
        }
    }

    // Obtener los datos de una region por id (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Regiones> getRegionById (@PathVariable Long id){
        try {
            Regiones regiones = regionesService.getRegionesById(id);
            return ResponseEntity.status(HttpStatus.OK).body(regiones);
        }catch (RegionNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar una region (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Regiones> updateRegion (@RequestBody Regiones region, @PathVariable Long id){
        try {
            Regiones regiones = regionesService.updateRegiones(region, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(regiones);
        } catch (RegionNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una region (DELETE)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Regiones> deleteRegionesById (@PathVariable Long id){
        try {
            regionesService.deleteRegionesById(id);
            return ResponseEntity.noContent().build();
        } catch (RegionNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


}
