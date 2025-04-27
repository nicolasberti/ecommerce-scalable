package com.microservice.orderservice.services.Events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.orderservice.models.DTOS.OrdenDTO;
import com.microservice.orderservice.models.Orden;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendOrden {

    private static final String TOPIC = "ordenes";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void enviarOrden(Orden orden) {
        try {
            //String ordenJson = objectMapper.writeValueAsString(orden);
            String ordenJson = objectMapper.writeValueAsString(Map.of(
                "id", orden.getId(),
                "productos", orden.getProductos()
            ));            
            kafkaTemplate.send(TOPIC, ordenJson);
			System.out.println("Enviando MSG: "+ordenJson);
        } catch (Exception e) {
			System.out.println("Error al enviar el MSG");
            e.printStackTrace();
        }
    }
}
