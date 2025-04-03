package com.microservice.productcatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.productcatalog.models.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    
}
