package com.microservice.productcatalog.services;

import org.springframework.stereotype.Service;

import com.microservice.productcatalog.models.Categoria;
import com.microservice.productcatalog.models.Producto;
import com.microservice.productcatalog.models.requests.UpdateProductoRequest;
import com.microservice.productcatalog.repositories.CategoriaRepository;
import com.microservice.productcatalog.repositories.ProductoRepository;

@Service
public class UpdateProduct {
    
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public UpdateProduct(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    public Producto updateProduct(Long id, UpdateProductoRequest updateProductoRequest){

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if(updateProductoRequest.getNombre() != null )
            if(!updateProductoRequest.getNombre().equals(""))
                producto.setNombre(updateProductoRequest.getNombre());

        if(updateProductoRequest.getDescripcion() != null)
            if(!updateProductoRequest.getDescripcion().equals(""))
                producto.setDescripcion(updateProductoRequest.getDescripcion());

        if(updateProductoRequest.getPrecio() != null)
            producto.setPrecio(updateProductoRequest.getPrecio());

        if(updateProductoRequest.getStock() != null)
            producto.setStock(updateProductoRequest.getStock());
            
        if(updateProductoRequest.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(updateProductoRequest.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(categoria);
        }

        return productoRepository.save(producto); 
    }
}
