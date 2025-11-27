package com.amanalli.back.controller;

import com.amanalli.back.exceptions.RegionNotFoundException;
import com.amanalli.back.model.Region;
import com.amanalli.back.service.RegionesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/regiones")
public class RegionController {
    // === Inyección del service ===
    private final RegionesService regionesService;

    @Autowired
    public RegionController(RegionesService regionesService) {
        this.regionesService = regionesService;
    }

    // === Obtener la información de todas las regiones (GET) ===
    @GetMapping
    public List<Region> getCategorias(){
        return regionesService.getCategorias();
    }

    // === Crear nueva region (POST) ===
    @PostMapping("/nueva-region")
    public ResponseEntity<Region> crearRegion (@RequestBody Region region){
        Region findRegion = regionesService.findByNombreRegion(region.getNombreRegion());
        if (findRegion != null){
            // 409 conflict
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            //201 created
            return ResponseEntity.status(HttpStatus.CREATED).body(regionesService.createRegion(region));
        }
    }

    // === Obtener los datos de una región por ID (GET) ===
    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegionById (@PathVariable Long id){
        try {
            Region region = regionesService.getRegionesById(id);
            return ResponseEntity.status(HttpStatus.OK).body(region);
        }catch (RegionNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // === Actualizar una region activa (PUT) ===
    @PutMapping("/{id}")
    public ResponseEntity<Region> updateRegion (@RequestBody Region region, @PathVariable Long id){
        try {
            Region regiones = regionesService.updateRegiones(region, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(regiones);
        } catch (RegionNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // === Activar una region Estatus = True (PUT) ===
    @Transactional
    @PutMapping("/activar/{id}")
    public ResponseEntity<Region> updateRegionesInactivas (@PathVariable Long id) {
        try {
            Region region = regionesService.updateRegionesInactivas(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(region);
        } catch (RegionNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    // === Eliminar/desactivar una region Estatus = False (DELETE) ===
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegionesById (@PathVariable Long id){
        try {
            regionesService.deleteRegionesById(id);
            return ResponseEntity.noContent().build();
        } catch (RegionNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


}
