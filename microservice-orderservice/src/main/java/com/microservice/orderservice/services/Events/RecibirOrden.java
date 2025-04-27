package com.microservice.orderservice.services.Events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.orderservice.models.DTOS.OrdenChangeDTO;
import com.microservice.orderservice.services.Orden.OrdenService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecibirOrden {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OrdenService ordenService;

    public RecibirOrden(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @KafkaListener(topics = "cambiar-estado-orden", groupId = "orden-service")
    @Transactional
    public void recibirOrden(String ordenMsg) {
        OrdenChangeDTO orden = null;
        try {
            System.out.println("OrderService : Se recibió la orden: "+ordenMsg);
            orden = objectMapper.readValue(ordenMsg, OrdenChangeDTO.class);
            ordenService.modificarOrden(orden.getId(), orden.getStatus());
        } catch (Exception e) {
            System.out.println("OrderService : Error: "+e);
            e.printStackTrace();
        }
    }
}

