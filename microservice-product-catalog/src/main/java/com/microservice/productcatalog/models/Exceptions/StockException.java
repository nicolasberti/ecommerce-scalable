package com.microservice.productcatalog.models.Exceptions;

public class StockException extends Exception{
    public StockException(String message) {
        super(message);
    }
}
