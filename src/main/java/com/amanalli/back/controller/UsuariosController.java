package com.amanalli.back.controller;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.exceptions.UsuarioNotFoundException;
import com.amanalli.back.model.Usuarios;
import com.amanalli.back.service.UsuariosService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping
    public ResponseEntity<List<Usuarios>> getUsuarios() {
        return ResponseEntity.ok().body(usuariosService.getUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> getUsuarioById(@PathVariable Long id) {
        try {
            Usuarios usuario = usuariosService.getUserByIdAndActivoTrue(id);
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        } catch (UsuarioNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Usuarios> crearUsuario(@RequestBody Usuarios usuarios) {
        Usuarios usuarioByNombre = usuariosService.findByNombreCompleto(usuarios.getNombreCompleto());
        Usuarios usuarioByEmail = usuariosService.findByEmail(usuarios.getEmail());
        Usuarios usuarioByTelefono = usuariosService.findByTelefono(usuarios.getTelefono());
        if (usuarioByNombre != null || usuarioByEmail != null || usuarioByTelefono != null) {
            //409 conflict
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            //201 created, hay que pasarle un body
            return ResponseEntity.status(HttpStatus.CREATED).body(usuariosService.crearUsuario(usuarios));
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Usuarios> updateUsuario(@RequestBody Usuarios usuario,@PathVariable Long id) {
        try {
            Usuarios usuarios = usuariosService.updateUsuarios(usuario, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarios);
        } catch (UsuarioNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // === Activar un usuario Estatus = True (PUT) ===
    @Transactional
    @PutMapping("/activar/{id}")
    public ResponseEntity<Usuarios> activarUsuarioInactivo(@PathVariable Long id) {
        try {
            Usuarios usuarios = usuariosService.activarUsuarioInactivo(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarios);
        } catch (CategoriaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // === Eliminar/desactivar un usuario Estatus = False (DELETE) ===
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuarioById(@PathVariable Long id) {
        try {
            usuariosService.deleteUsuariosById(id);
            return ResponseEntity.noContent().build();
        } catch (CategoriaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

