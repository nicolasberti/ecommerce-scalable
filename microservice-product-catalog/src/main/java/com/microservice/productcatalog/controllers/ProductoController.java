package com.microservice.productcatalog.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.microservice.productcatalog.models.Producto;
import com.microservice.productcatalog.models.requests.CreateProductRequest;
import com.microservice.productcatalog.models.requests.UpdateProductoRequest;
import com.microservice.productcatalog.repositories.ProductoRepository;
import com.microservice.productcatalog.services.CreateProduct;
import com.microservice.productcatalog.services.UpdateProduct;
import org.springframework.cache.annotation.Cacheable;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductoController {
    
    private final ProductoRepository productoRepository;
    private final CreateProduct createProduct;
    private final UpdateProduct updateProduct;

    public ProductoController(ProductoRepository productoRepository, CreateProduct createProduct, UpdateProduct updateProduct) {
        this.productoRepository = productoRepository;
        this.createProduct = createProduct;
        this.updateProduct = updateProduct;
    }

    @GetMapping
    //@Cacheable(value = "productos") descomentar cuando funcione redis
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductRequest CreateProductRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }

        Producto producto = createProduct.createProduct(CreateProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    
            productoRepository.deleteById(id);
    
            return ResponseEntity.status(HttpStatus.OK).body(producto);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
    
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductoRequest updateProductoRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())    
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }
        Producto producto = updateProduct.updateProduct(id, updateProductoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(producto);
    }
}
