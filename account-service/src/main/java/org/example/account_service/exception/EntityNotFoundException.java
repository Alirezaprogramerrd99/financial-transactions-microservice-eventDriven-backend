package org.example.account_service.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String nationalCode) {
        super("Account with NationalCode " + nationalCode + " does not exist");
    }
}
