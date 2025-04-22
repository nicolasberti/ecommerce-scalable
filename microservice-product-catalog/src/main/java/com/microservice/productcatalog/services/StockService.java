package com.microservice.productcatalog.services;

import com.microservice.productcatalog.models.Exceptions.StockException;
import com.microservice.productcatalog.models.Producto;
import com.microservice.productcatalog.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final ProductoRepository productoRepository;

    public StockService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public float reducirStock(Long productoId, int cantidad) throws Exception {
        Producto producto = productoRepository.findById(productoId).orElse(null);
        if(producto == null)
            throw new StockException("Producto inválido");
        float totalPrice;

        if (producto.getStock() >= cantidad) {
            producto.setStock(producto.getStock() - cantidad);
            totalPrice = producto.getPrecio() * cantidad;
        } else {
            throw new StockException("Stock insuficiente");
        }

        return totalPrice;

    }
}
