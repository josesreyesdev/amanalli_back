package com.amanalli.back.mappers;

public record UsuarioResponse(
        Long idUsuario,
        String nombreCompleto,
        String email,
        String telefono,
        Boolean activo
) {
}
