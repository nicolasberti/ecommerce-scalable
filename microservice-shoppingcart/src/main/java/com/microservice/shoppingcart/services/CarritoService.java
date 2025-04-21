package com.microservice.shoppingcart.services;

import java.util.List;
import java.util.Map;

import com.microservice.shoppingcart.models.ProductoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.shoppingcart.models.Carrito;
import com.microservice.shoppingcart.repositories.CartRepository;

@Service
public class CarritoService {

    @Autowired
    private CartRepository cartRepository;

    public List<Carrito> getAllCarts() {
        return cartRepository.findAll();
    }

    public Carrito getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void createCart(Long userId) {
        Carrito cart = new Carrito();
        cart.setUserId(userId);
        cart.setProducts(List.of());
        cartRepository.save(cart);
    }

    public Carrito addProductToCart(Long userId, ProductoDTO product) {
        Carrito cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            createCart(userId);
            cart = cartRepository.findByUserId(userId);
        }
        // Si el producto ya está dentro de la lista, simplemente actualiza la cantidad.
        for(int i = 0; i < cart.getProducts().size(); i++) {
            if(cart.getProducts().get(i).getId().equals(product.getId())) {
                long cantidad = cart.getProducts().get(i).getCantidad() + product.getCantidad();
                cart.getProducts().get(i).setCantidad(cantidad);
                return cartRepository.save(cart);
            }
        }
        List<ProductoDTO> products = cart.getProducts();
        products.add(product);
        cart.setProducts(products);
        return cartRepository.save(cart);
    }

    public Carrito removeProductFromCart(Long userId, Long productIndex) {
        Carrito cart = cartRepository.findByUserId(userId);
        List<ProductoDTO> products = cart.getProducts();

        for(int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i).getId());
            if (products.get(i).getId() == productIndex) {
                products.remove(i);
                cart.setProducts(products);
                return cartRepository.save(cart);
            }
        }
        return cart;
    }

    public Carrito deleteCart(Long userId) {
        Carrito cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cartRepository.delete(cart);
        }
        return cart;
    }
}