package com.microservice.orderservice.repositories;

import com.microservice.orderservice.models.Orden;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends MongoRepository<Orden, String> {
}
