package com.amanalli.back.exceptions;

public class VentaPedidosNotFoundException extends RuntimeException {
    public VentaPedidosNotFoundException(Long id) {
        super("Venta no encontrada: " + id);
    }
}
