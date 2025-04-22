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
public class ConsultarStock {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StockService stockService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ConsultarStock(StockService stockService, KafkaTemplate<String, String> kafkaTemplate) {
        this.stockService = stockService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "ordenes", groupId = "stock-service")
    @Transactional
    public void recibirOrden(String ordenMsg) {
        OrdenDTO orden = null;
        try {
            orden = objectMapper.readValue(ordenMsg, OrdenDTO.class);
            float totalPrice = 0;
            for(int i = 0; i < orden.getProductos().size(); i++) {
                ProductoOrden producto = orden.getProductos().get(i);
                totalPrice += stockService.reducirStock(producto.getId(), producto.getCantidad());
            }
            String mensaje = "{\"orderId\": " + orden.getOrderId() + ", \"totalPrice\": " + totalPrice + "}";
            kafkaTemplate.send("pagos", mensaje);
        } catch (Exception e) {
            if(orden != null /*&& e instanceof StockException*/) {
                String msgRollback = "{\"orderId\": " + orden.getOrderId() + "}";
                kafkaTemplate.send("rollback-orden", msgRollback);
            }
            e.printStackTrace();
        }
    }
}
