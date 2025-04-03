package com.microservice.productcatalog.services;

import org.springframework.stereotype.Service;

import com.microservice.productcatalog.models.Categoria;
import com.microservice.productcatalog.models.Producto;
import com.microservice.productcatalog.models.requests.CreateProductRequest;
import com.microservice.productcatalog.repositories.CategoriaRepository;
import com.microservice.productcatalog.repositories.ProductoRepository;

@Service
public class CreateProduct {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public CreateProduct(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    public Producto createProduct(CreateProductRequest CreateProductRequest){
        Categoria categoria = categoriaRepository.findById(CreateProductRequest.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

            Producto producto = new Producto();
            producto.setNombre(CreateProductRequest.getNombre());
            producto.setDescripcion(CreateProductRequest.getDescripcion());
            producto.setPrecio(CreateProductRequest.getPrecio());
            producto.setStock(CreateProductRequest.getStock());
            producto.setCategoria(categoria);

        return productoRepository.save(producto); 
    }
    
}
