package com.amanalli.back.exceptions;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Long id) {
        super("Categoria no encontrada: " + id);
    }
}
