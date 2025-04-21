package com.microservice.productcatalog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservice.productcatalog.models.Categoria;
import com.microservice.productcatalog.repositories.CategoriaRepository;


@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    // Crear servicios para delegar responsabilidades, por simplicidad usaremos el repositorio. Próximo refactor hacer eso
    // Objetivo de momento aprender micro-servicios.
    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    
    @GetMapping
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Categoria categoria) {
        Categoria categoria = categoriaRepository.findById(id).get();

        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }*/
    


}
