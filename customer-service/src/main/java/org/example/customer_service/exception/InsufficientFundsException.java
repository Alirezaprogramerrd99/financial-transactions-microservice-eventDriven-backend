package org.example.customer_service.exception;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException() {
        super("Insufficient funds for withdrawal");
    }
}
