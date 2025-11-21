package com.amanalli.back.repository;

import com.amanalli.back.model.Regiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionesRepository extends JpaRepository<Regiones, Long> {
}
