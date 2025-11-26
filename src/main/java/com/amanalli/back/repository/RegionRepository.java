package com.amanalli.back.repository;

import com.amanalli.back.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findByNombreRegion(String nombreRegion);

    @Query("SELECT c FROM Region c WHERE c.estatusRegion = true")
    List<Region> findRegionesActivas();

    @Query("SELECT c FROM Region c WHERE c.idRegion = :id AND c.estatusRegion = true")
    Optional<Region> findByIdAndActivo(Long id);
}
