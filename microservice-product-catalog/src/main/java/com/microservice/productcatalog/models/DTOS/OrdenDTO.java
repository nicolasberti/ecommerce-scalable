package com.microservice.productcatalog.models.DTOS;

import java.util.List;

public class OrdenDTO {
    private String orderId;
    private List<ProductoOrden> productos;

    public OrdenDTO(String orderId, List<ProductoOrden> productos) {
        this.orderId = orderId;
        this.productos = productos;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<ProductoOrden> getProductos() {
        return productos;
    }
    
}
