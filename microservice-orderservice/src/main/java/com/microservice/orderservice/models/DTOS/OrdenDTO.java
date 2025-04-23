package com.microservice.orderservice.models.DTOS;

import com.microservice.orderservice.models.ProductoOrden;

import java.util.List;

public class OrdenDTO {
    private List<ProductoOrden> productos;

    public OrdenDTO() {}
    public OrdenDTO(List<ProductoOrden> productos) {
        this.productos = productos;
    }

    public List<ProductoOrden> getProductos() {
        return productos;
    }
    public void setProductos(List<ProductoOrden> productos) {
        this.productos = productos;
    }
}
