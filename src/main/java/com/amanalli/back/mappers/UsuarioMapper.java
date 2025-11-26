package com.amanalli.back.mappers;

import com.amanalli.back.model.Rol;
import com.amanalli.back.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UsuarioMapper {
    public static UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getIdUsuario(),
                usuario.getNombreCompleto(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getActivo()
        );
    }

    public static Usuario toModel(UsuarioRequest request, List<Rol> roles, PasswordEncoder encoder) {
        return new Usuario(null, request.nombreCompleto(), request.email(), encoder.encode(request.password()), request.telefono(), roles);
    }
}
