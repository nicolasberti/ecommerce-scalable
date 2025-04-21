package com.microservice.shoppingcart.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "carts")
public class Carrito {

    @Id
    private String id;
    private Long userId;
    private List<ProductoDTO> products;

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ProductoDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductoDTO> products) {
        this.products = products;
    }
}