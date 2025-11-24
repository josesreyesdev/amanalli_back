package com.amanalli.back.exceptions;

public class RegionNotFoundException extends RuntimeException {
    public RegionNotFoundException(Long id) {
        super("Región no encontrada: " + id);
    }
}