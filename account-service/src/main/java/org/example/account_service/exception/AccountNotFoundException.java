package org.example.account_service.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accountNumber) {
        super("Account with accountNumber " + accountNumber + " does not exist");
    }
}
