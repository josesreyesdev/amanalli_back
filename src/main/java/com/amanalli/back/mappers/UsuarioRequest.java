package com.amanalli.back.mappers;

import java.util.List;

public record UsuarioRequest(
        String nombreCompleto,
        String email,
        String password,
        String telefono,
        List<Long> roles
) {
}
