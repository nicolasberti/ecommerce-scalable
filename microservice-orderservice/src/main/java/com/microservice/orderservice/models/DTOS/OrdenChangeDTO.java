package com.microservice.orderservice.models.DTOS;

import com.microservice.orderservice.models.OrdenStatus;

public class OrdenChangeDTO {
    String id;
    OrdenStatus status;

    public OrdenChangeDTO() { }
    public OrdenChangeDTO(String id, OrdenStatus status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public OrdenStatus getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(OrdenStatus status) {
        this.status = status;
    }
}
