package org.example.account_service.exception;

public class NationalCodeNotFoundException extends RuntimeException {
    public NationalCodeNotFoundException(String nationalCode) {
        super("national code " + nationalCode + "not found!");
    }
}
