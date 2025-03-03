package org.example.customer_service.exception;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(Integer id) {
        super("entity with id " + id + " not found");
    }
}
