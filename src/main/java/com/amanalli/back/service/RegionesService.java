package com.amanalli.back.service;

import com.amanalli.back.exceptions.RegionNotFoundException;
import com.amanalli.back.model.Region;
import com.amanalli.back.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionesService {
    // === Inyección del Repository ===
    private final RegionRepository regionRepository;

    @Autowired
    public RegionesService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    // === Obtener la información de todas las regiones (GET) ===
    public List<Region> getCategorias() {
        return regionRepository.findRegionesActivas();
    }

    // === Crear nueva region (POST) ===
    public Region createRegion(Region region) {
        return regionRepository.save(region);
    }

    public Region findByNombreRegion(String nombreRegion) {
        return regionRepository.findByNombreRegion(nombreRegion);
    }

    // === Obtener los datos de una región por ID (GET) ===
    public Region getRegionesById(Long id) {
        Optional<Region> regiones = regionRepository.findByIdAndActivo(id);
        return regiones.orElseThrow(() -> new RegionNotFoundException(id));
    }

    // === Actualizar una region activa (PUT) ===
    public Region updateRegiones(Region region, Long id) {
        return regionRepository.findByIdAndActivo(id)
                .map(regiones -> {
                    regiones.setNombreRegion(region.getNombreRegion());
                    return regionRepository.save(regiones);
                })
                .orElseThrow(() -> new RegionNotFoundException(id));
    }

    // === Activar una region Estatus = True (PUT) ===
    public Region updateRegionesInactivas (Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RegionNotFoundException(id));

        region.activar();
        return regionRepository.save(region);
    }

    // === Eliminar/desactivar una region Estatus = False (DELETE) ===
    public void deleteRegionesById(Long id) {
        if (!regionRepository.existsById(id)){
            throw new RegionNotFoundException(id);
        }
        Region region = regionRepository.getReferenceById(id);
        region.desactivarById();
    }
}
