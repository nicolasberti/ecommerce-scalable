package com.microservice.productcatalog.models.DTOS;

import java.util.List;

public class OrdenDTO {
    private String id;
    private List<ProductoOrden> productos;

	public OrdenDTO() {}
	
    public OrdenDTO(String id, List<ProductoOrden> productos) {
        this.id = id;
        this.productos = productos;
    }

    public String getId() {
        return id;
    }

    public List<ProductoOrden> getProductos() {
        return productos;
    }
	
	public void setId(String id){
		this.id = id;
	}
	
	public void setProductos(List<ProductoOrden> productos){
		this.productos = productos;
	}
    
}
