package com.amanalli.back.service;

import com.amanalli.back.exceptions.UsuarioNotFoundException;
import com.amanalli.back.model.Usuarios;
import com.amanalli.back.repository.UsuariosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;

    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public List<Usuarios> getUsuarios() {
        return usuariosRepository.findAllAndActivoTrue();
    }

    public Usuarios getUserByIdAndActivoTrue(Long id) {
        Optional<Usuarios> usuarioOptional = usuariosRepository.findByIdAndActivoTrue(id);
        return usuarioOptional.orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    public Usuarios crearUsuario(Usuarios usuarios) {
        return usuariosRepository.save(usuarios);
    }


    public Usuarios findByNombreCompleto(String nombreCompleto) {
        return usuariosRepository.findByNombreCompleto(nombreCompleto);
    }

    public Usuarios findByEmail(String email) {
        return usuariosRepository.findByEmail(email);
    }

    public Usuarios findByTelefono(String telefono) {
        return usuariosRepository.findByTelefono(telefono);
    }

    // === Actualizar un usuario activo (PUT) ===
    public Usuarios updateUsuarios(Usuarios usuarios, Long id) {
        return usuariosRepository.findByIdAndActivoTrue(id)
                .map(usr -> {
                    usr.setNombreCompleto(usuarios.getNombreCompleto());
                    usr.setEmail(usuarios.getEmail());
                    usr.setPassword(usuarios.getPassword());
                    usr.setTelefono(usuarios.getTelefono());
                    return usuariosRepository.save(usr);
                })
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    // === Activar una usuario Estatus = True (PUT) ===
    public Usuarios activarUsuarioInactivo(Long id) {
        Usuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        usuario.activar();
        return usuariosRepository.save(usuario);
    }

    // === Eliminar/desactivar un usuario Estatus = False (DELETE) ===
    public void deleteUsuariosById(Long id) {
        if (!usuariosRepository.existsById(id)) {
            throw new UsuarioNotFoundException(id);
        }
        Usuarios usuarios = usuariosRepository.getReferenceById(id);
        usuarios.desactivar();
    }
}
