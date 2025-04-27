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
		System.out.println("Se recibió la orden: "+ordenMsg);
        OrdenDTO orden = null;
        try {
            orden = objectMapper.readValue(ordenMsg, OrdenDTO.class);
            float totalPrice = 0;
            for(int i = 0; i < orden.getProductos().size(); i++) {
                ProductoOrden producto = orden.getProductos().get(i);
                totalPrice += stockService.reducirStock(producto.getId(), producto.getCantidad());
            }
            String mensaje = "{\"id\": " + orden.getId() + ", \"totalPrice\": " + totalPrice + "}";
            kafkaTemplate.send("pagos", mensaje);
			System.out.println("Se envió a pagar: "+mensaje);
        } catch (Exception e) {
            if(orden != null /*&& e instanceof StockException*/) {
                String msgRollback = "{\"id\": \"" + orden.getId() + "\", \"status\": \"CANCELADA\" }";
                kafkaTemplate.send("cambiar-estado-orden", msgRollback);
                System.out.println("Se envió a cambiar el estado de la orden: "+msgRollback);
            }
            e.printStackTrace();
        }
    }
}
