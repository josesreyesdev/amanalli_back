package com.amanalli.back.service;

import com.amanalli.back.exceptions.UsuarioNotFoundException;
import com.amanalli.back.mappers.UsuarioMapper;
import com.amanalli.back.mappers.UsuarioRequest;
import com.amanalli.back.model.Rol;
import com.amanalli.back.model.Usuario;
import com.amanalli.back.repository.RolRepository;
import com.amanalli.back.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.encoder = encoder;
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAllAndActivoTrue();
    }

    public Usuario getUsuarioByIdAndActivoTrue(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndActivoTrue(id);
        return usuarioOptional.orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    public Usuario crearUsuario(UsuarioRequest request) {
        List<Rol> roles;

        if (request.roles() == null || request.roles().isEmpty()) {
            Rol rolPorDefecto = rolRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));
            roles = List.of(rolPorDefecto);
        } else {
            roles = request.roles().stream()
                    .map(currentId -> rolRepository.findById(currentId)
                            .orElseThrow(() -> new UsuarioNotFoundException(currentId)))
                    .toList();
        }

        return usuarioRepository.save(UsuarioMapper.toModel(request, roles, encoder));
    }


    public Usuario findByNombreCompleto(String nombreCompleto) {
        return usuarioRepository.findByNombreCompleto(nombreCompleto);
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario findByTelefono(String telefono) {
        return usuarioRepository.findByTelefono(telefono);
    }

    // === Actualizar un usuario activo (PUT) ===
    public Usuario updateUsuarios(UsuarioRequest request, Long id) {
        return usuarioRepository.findByIdAndActivoTrue(id)
                .map(usr -> {
                    usr.setNombreCompleto(request.nombreCompleto());
                    usr.setEmail(request.email());
                    usr.setPassword(encoder.encode(request.password()));
                    usr.setTelefono(request.telefono());
                    if (!request.roles().isEmpty()) {
                        List<Rol> roles = request.roles().stream()
                                .map(currentId -> rolRepository.findById(currentId)
                                        .orElseThrow(() -> new UsuarioNotFoundException(currentId)))
                                .toList();
                        usr.setRoles(roles);
                    }
                    return usuarioRepository.save(usr);
                })
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    // === Activar una usuario Estatus = True (PUT) ===
    public Usuario activarUsuarioInactivo(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        usuario.activar();
        return usuarioRepository.save(usuario);
    }

    // === Eliminar/desactivar un usuario Estatus = False (DELETE) ===
    public void deleteUsuariosById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNotFoundException(id);
        }
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.desactivar();
    }
}
