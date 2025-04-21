package com.microservice.shoppingcart.repositories;

import com.microservice.shoppingcart.models.ProductoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.microservice.shoppingcart.models.Carrito;

public interface CartRepository extends MongoRepository<Carrito, String> {
    Carrito findByUserId(Long userId);
}