package com.microservice.orderservice.services.Events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.orderservice.models.DTOS.OrdenDTO;
import com.microservice.orderservice.models.Orden;
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
            OrdenDTO ordenDTO = new OrdenDTO(orden.getId(), orden.getProductos());
            String ordenJson = objectMapper.writeValueAsString(ordenDTO);
            kafkaTemplate.send(TOPIC, ordenJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
