package com.microservice.productcatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.productcatalog.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
}
