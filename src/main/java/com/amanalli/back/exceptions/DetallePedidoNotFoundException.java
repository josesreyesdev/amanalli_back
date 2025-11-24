package com.amanalli.back.exceptions;

public class DetallePedidoNotFoundException extends RuntimeException {
    public DetallePedidoNotFoundException(Long id) {
        super("No se encontró el detalle del pedido con ID: " + id);
    }
}
