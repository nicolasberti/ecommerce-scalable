package com.microservice.orderservice.models;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Entity
@Document(collection = "orders")
public class Orden {
    @Id
    private String id;
    private List<ProductoOrden> productos;
    private OrdenStatus status;

    public Orden() {}

    public Orden(String id, List<ProductoOrden> productos, OrdenStatus status) {
        this.id = id;
        this.productos = productos;
        this.status = status;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<ProductoOrden> getProductos() {
        return productos;
    }
    public void setProductos(List<ProductoOrden> productos) {
        this.productos = productos;
    }
    public OrdenStatus getStatus() {
        return status;
    }
    public void setStatus(OrdenStatus status) {
        this.status = status;
    }
}
