package org.example.customer_service.exception;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String nationalCode) {
        super("More than this customers with national code " + nationalCode + " are registered in the system");
    }
}
