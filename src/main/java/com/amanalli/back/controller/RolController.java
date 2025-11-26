package com.amanalli.back.controller;

import com.amanalli.back.model.Rol;
import com.amanalli.back.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    private final RolRepository repository;

    @Autowired
    public RolController(RolRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) {
        Rol findRolByName = repository.findByNombre(rol.getNombre());
        if (findRolByName != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        var saveRol = repository.save(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveRol);
    }

    @GetMapping
    public ResponseEntity<List<Rol>> getAll() {
        return ResponseEntity.ok().body(repository.findAll());
    }
}
