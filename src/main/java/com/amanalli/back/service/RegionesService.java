package com.amanalli.back.service;

import com.amanalli.back.exceptions.RegionNotFoundException;
import com.amanalli.back.model.Regiones;
import com.amanalli.back.repository.RegionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionesService {
    // === Inyección del Repository ===
    private final RegionesRepository regionesRepository;

    @Autowired
    public RegionesService(RegionesRepository regionesRepository) {
        this.regionesRepository = regionesRepository;
    }

    // === Obtener la información de todas las regiones (GET) ===
    public List<Regiones> getCategorias() {
        return regionesRepository.findRegionesActivas();
    }

    // === Crear nueva region (POST) ===
    public Regiones createRegion(Regiones regiones) {
        return regionesRepository.save(regiones);
    }

    public Regiones findByNombreRegion(String nombreRegion) {
        return regionesRepository.findByNombreRegion(nombreRegion);
    }

    // === Obtener los datos de una región por ID (GET) ===
    public Regiones getRegionesById(Long id) {
        Optional<Regiones> regiones = regionesRepository.findByIdAndActivo(id);
        return regiones.orElseThrow(() -> new RegionNotFoundException(id));
    }

    // === Actualizar una region activa (PUT) ===
    public Regiones updateRegiones(Regiones region, Long id) {
        return regionesRepository.findByIdAndActivo(id)
                .map(regiones -> {
                    regiones.setNombreRegion(region.getNombreRegion());
                    return regionesRepository.save(regiones);
                })
                .orElseThrow(() -> new RegionNotFoundException(id));
    }

    // === Activar una region Estatus = True (PUT) ===
    public Regiones updateRegionesInactivas (Long id) {
        Regiones region = regionesRepository.findById(id)
                .orElseThrow(() -> new RegionNotFoundException(id));

        region.activar();
        return regionesRepository.save(region);
    }

    // === Eliminar/desactivar una region Estatus = False (DELETE) ===
    public void deleteRegionesById(Long id) {
        if (!regionesRepository.existsById(id)){
            throw new RegionNotFoundException(id);
        }
        Regiones regiones = regionesRepository.getReferenceById(id);
        regiones.desactivarById();
    }
}
