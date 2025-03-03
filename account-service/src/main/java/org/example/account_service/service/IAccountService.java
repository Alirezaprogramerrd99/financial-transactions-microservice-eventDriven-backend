package org.example.account_service.service;

import org.example.account_service.model.Account;
import org.example.account_service.model.dto.AccountUpdateRequest;
import org.example.account_service.model.dto.CustomerSummary;

public interface IAccountService {
    Account createAccount(Integer customerId);

    String findAccountNumberByNationalCode(String nationalCode);

    Double findBalanceByAccountNumber(String accountNumber);

    Boolean existByNationalCode(String nationalCode);

    Boolean existsByAccountNumber(String accountNumber);

    CustomerSummary getByAccountNumber(String accountNumber);

    Account findById(Integer id);

    void update(AccountUpdateRequest accountUpdateRequest);

    Account findByAccountNumber(String accountNumber);

    void pureSave(Account account);
}

