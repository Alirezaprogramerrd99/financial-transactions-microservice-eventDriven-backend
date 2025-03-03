package org.example.account_service.exception;

public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException() {
        super("the source and destination account numbers cannot be the same");
    }
}
