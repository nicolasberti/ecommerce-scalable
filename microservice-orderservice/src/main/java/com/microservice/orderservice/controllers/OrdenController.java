package com.microservice.orderservice.controllers;

import com.microservice.orderservice.models.DTOS.OrdenDTO;
import com.microservice.orderservice.services.Orden.OrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping
    public ResponseEntity<?> getOrders() {
        return ResponseEntity.ok(ordenService.getOrders());
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrdenDTO orden) {
        ordenService.createOrder(orden);
        return ResponseEntity.ok("{\"message\": \"Order created successfully with status PENDIENTE\"}");
    }
}
