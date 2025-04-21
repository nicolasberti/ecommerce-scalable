package com.microservice.shoppingcart.controllers;

import com.microservice.shoppingcart.models.ProductoDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.microservice.shoppingcart.services.CarritoService;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class Cart {
    
    public CarritoService cartService;

    public Cart(CarritoService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long userId, @RequestBody @Valid ProductoDTO product) {
        return ResponseEntity.ok(cartService.addProductToCart(userId, product));
    }

    @DeleteMapping("/{userId}/{productIndex}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long userId, @PathVariable long productIndex) {
        return ResponseEntity.ok(cartService.removeProductFromCart(userId, productIndex));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.deleteCart(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }


    
}
