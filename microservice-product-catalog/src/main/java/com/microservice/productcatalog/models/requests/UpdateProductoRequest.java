package com.microservice.productcatalog.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateProductoRequest {

    private String nombre;
    private String descripcion;
    private Float precio;
    private Integer stock;
    private Long categoriaId;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Float getPrecio() {
        return precio;
    }
    public void setPrecio(Float precio) {
        this.precio = precio;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Long getCategoriaId() {
        return categoriaId;
    }
    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
