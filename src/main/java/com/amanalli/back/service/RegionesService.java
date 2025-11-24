package com.amanalli.back.service;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.exceptions.RegionNotFoundException;
import com.amanalli.back.model.Categorias;
import com.amanalli.back.model.Regiones;
import com.amanalli.back.repository.RegionesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionesService {
    private final RegionesRepository regionesRepository;

    @Autowired
    public RegionesService(RegionesRepository regionesRepository) {
        this.regionesRepository = regionesRepository;
    }

    // Obtener la información de todos los usuarios (GET)
    public List<Regiones> getCategorias() {
        return regionesRepository.findRegionesActivas();
    }

    public Regiones findByNombreRegion(String nombreRegion) {
        return regionesRepository.findByNombreRegion(nombreRegion);
    }

    // Nueva Region
    public Regiones createRegion(Regiones regiones) {
        return regionesRepository.save(regiones);
    }

    // Obtener los datos de una region (GET)
    public Regiones getRegionesById(Long id) {
        Optional<Regiones> regioness = regionesRepository.findByIdAndActivo(id);
        return regioness.orElseThrow(() -> new RegionNotFoundException(id));
    }

    // Actualizar una region (PUT)
    public Regiones updateRegiones(Regiones region, Long id) {
        return regionesRepository.findByIdAndActivo(id)
                .map(regiones -> {
                    regiones.setNombreRegion(region.getNombreRegion());
                    regiones.setEstatusRegion(region.getEstatusRegion());
                    return regionesRepository.save(regiones);
                })
                .orElseThrow(() -> new RegionNotFoundException(id));
    }

    // Eliminar una region (DELETE)
    public void deleteRegionesById(Long id) {
        if (!regionesRepository.existsById(id) || regionesRepository.existsByIdAndActivoFalse(id)){
            throw new RegionNotFoundException(id);
        }
        Regiones regiones = regionesRepository.getReferenceById(id);
        regiones.desactivarById();
    }
}
