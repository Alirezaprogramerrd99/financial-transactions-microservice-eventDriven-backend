package org.example.customer_service.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long trackingCode) {
        super("transaction not found for trackingCode: " + trackingCode);
    }
}
