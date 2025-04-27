package com.microservice.orderservice.models;

public class ProductoOrden {
    private long id;
    private int cantidad;

    public ProductoOrden() {}
    public ProductoOrden(long id, int cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }   

    public int getCantidad() {
        return cantidad;
    }   

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
