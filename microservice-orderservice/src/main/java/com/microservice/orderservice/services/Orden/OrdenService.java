package com.microservice.orderservice.services.Orden;

import com.microservice.orderservice.models.DTOS.OrdenDTO;
import com.microservice.orderservice.models.Orden;
import com.microservice.orderservice.models.OrdenStatus;
import com.microservice.orderservice.repositories.OrdenRepository;
import com.microservice.orderservice.services.Events.SendOrden;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final SendOrden sendOrden;

    public OrdenService(OrdenRepository ordenRepository, SendOrden sendOrden) {
        this.ordenRepository = ordenRepository;
        this.sendOrden = sendOrden;
    }

    public List<Orden> getOrders() {
        return ordenRepository.findAll();
    }

    public void createOrder(OrdenDTO orden) {
        Orden ordenCreated = new Orden();
        ordenCreated.setProductos(orden.getProductos());
        ordenCreated.setStatus(OrdenStatus.PENDIENTE);
        ordenRepository.save(ordenCreated);
        sendOrden.enviarOrden(ordenCreated);
    }

}
