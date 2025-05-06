package com.microservice.productcatalog.services.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.productcatalog.models.DTOS.OrdenDTO;
import com.microservice.productcatalog.models.DTOS.ProductoOrden;
import com.microservice.productcatalog.services.StockService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RollbackStock {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StockService stockService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public RollbackStock(StockService stockService, KafkaTemplate<String, String> kafkaTemplate) {
        this.stockService = stockService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "rollback-stock", groupId = "stock-service")
    @Transactional
    public void rollbackStock(String ordenMsg) {
        OrdenDTO orden = null;
        try {
            orden = objectMapper.readValue(ordenMsg, OrdenDTO.class);
            for(int i = 0; i < orden.getProductos().size(); i++) {
                ProductoOrden producto = orden.getProductos().get(i);
                stockService.incrementarStock(producto.getId(), producto.getCantidad());
            }
            // Rollback a orden, orden cancelada.
            String msgRollback = "{\"id\": \"" + orden.getId() + "\", \"status\": \"CANCELADA\" }";
            kafkaTemplate.send("cambiar-estado-orden", msgRollback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
