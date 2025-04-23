package com.microservice.productcatalog.models.DTOS;

public class ProductoOrden {
    private Long id;
    private int cantidad;

    public ProductoOrden(Long id, int cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public int getCantidad() {
        return cantidad;
    }
}
