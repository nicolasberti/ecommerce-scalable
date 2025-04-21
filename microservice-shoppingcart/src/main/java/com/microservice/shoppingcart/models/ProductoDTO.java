package com.microservice.shoppingcart.models;

import jakarta.validation.constraints.NotNull;

public class ProductoDTO {

    @NotNull(message = "El ID del producto no puede ser nulo.")
    private Long id;

    @NotNull(message = "La cantidad no puede ser nula.")
    private Long cantidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
