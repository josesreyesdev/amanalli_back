package com.amanalli.back.controller;

import com.amanalli.back.exceptions.CategoriaNotFoundException;
import com.amanalli.back.exceptions.UsuarioNotFoundException;
import com.amanalli.back.mappers.UsuarioMapper;
import com.amanalli.back.mappers.UsuarioRequest;
import com.amanalli.back.mappers.UsuarioResponse;
import com.amanalli.back.model.Usuario;
import com.amanalli.back.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getUsuarios() {
        return ResponseEntity.ok().body(usuarioService.getUsuarios().stream().map(UsuarioMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getUsuarioById(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.getUsuarioByIdAndActivoTrue(id);
            return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toResponse(usuario));
        } catch (UsuarioNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<UsuarioResponse> crearUsuario(@RequestBody UsuarioRequest request) {
        Usuario usuarioByEmail = usuarioService.findByEmail(request.email());
        Usuario usuarioByTelefono = usuarioService.findByTelefono(request.telefono());
        if (usuarioByEmail != null || usuarioByTelefono != null) {
            //409 conflict
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            //201 created, hay que pasarle un body
            Usuario usuario = usuarioService.crearUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toResponse(usuario));
        }
    }

    @SecurityRequirement(name = "bearer-key")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UsuarioResponse> updateUsuario(@RequestBody UsuarioRequest request, @PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.updateUsuarios(request, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toResponse(usuario));
        } catch (UsuarioNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @SecurityRequirement(name = "bearer-key")
    // === Activar un usuario Estatus = True (PUT) ===
    @Transactional
    @PutMapping("/activar/{id}")
    public ResponseEntity<UsuarioResponse> activarUsuarioInactivo(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.activarUsuarioInactivo(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toResponse(usuario));
        } catch (CategoriaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @SecurityRequirement(name = "bearer-key")
    // === Eliminar/desactivar un usuario Estatus = False (DELETE) ===
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuarioById(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuariosById(id);
            return ResponseEntity.noContent().build();
        } catch (CategoriaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email")
    public ResponseEntity<Usuario> getByEmail(@RequestParam String email) {
        Usuario userByEmail = usuarioService.findByEmail(email);
        if(userByEmail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userByEmail);
    }


}

