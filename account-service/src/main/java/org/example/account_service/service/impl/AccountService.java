package org.example.account_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.account_service.dao.AccountRepository;
import org.example.account_service.event.HistoryEventPublisher;
import org.example.account_service.exception.AccountNotFoundException;
import org.example.account_service.exception.IdNotFoundException;
import org.example.account_service.model.Account;
import org.example.account_service.model.dto.AccountUpdateRequest;
import org.example.account_service.model.dto.CustomerSummary;
import org.example.account_service.model.dto.HistoryDTO;
import org.example.account_service.model.enumutation.AccountType;
import org.example.account_service.service.CustomerServiceClient;
import org.example.account_service.service.IAccountService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.account_service.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository repository;
    private final CustomerServiceClient customerServiceClient;
    private final HistoryEventPublisher historyEventPublisher;

//    private final IHistoryService iHistoryService;

    @Override
    public Account createAccount(Integer customerID) {

        Account account = new Account();
        String s = generateUniqueAccountNumber();
        account.setAccountNumber(s);
        account.setAccountType(AccountType.ACTIVE);
        account.setBalance(0D);
        account.setCustomerId(customerID);
//        account.setNationalCode(nationalCode);
        return repository.save(account);
    }

    @Override
    public String findAccountNumberByNationalCode(String nationalCode) {
        // Step 1: Retrieve customer details from customer-service
        CustomerSummary customerSummary = customerServiceClient.getCustomerSummaryByNationalCode(nationalCode);

        if (customerSummary == null) {
            throw new EntityNotFoundException("Customer with nationalCode " + nationalCode + " not found");
        }

        Integer customerId = customerSummary.id(); // Extract customerId

        // Step 2: Retrieve the account using customerId
        return repository.findByCustomerId(customerId)
                .map(Account::getAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("No account found for customer with nationalCode: " + nationalCode));
    }

    @Override
    public Double findBalanceByAccountNumber(String accountNumber) {
        if (!existsByAccountNumber(accountNumber))
            throw new AccountNotFoundException(accountNumber);
        return repository.findBalanceByAccountNumber(accountNumber);
    }

    @Override
    public Boolean existByNationalCode(String nationalCode) {
        return customerServiceClient.existsByNationalCode(nationalCode);
    }

    @Override
    public Boolean existsByAccountNumber(String accountNumber) {
        return repository.existByAccountNumber(accountNumber);
    }

    @Override
    public CustomerSummary getByAccountNumber(String accountNumber) {
        // Step 1: Find the account in the database
        Account account = repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));

        // Step 2: Retrieve customer details from customer-service using customerId
        CustomerSummary customerSummary = customerServiceClient.getCustomerSummaryByNationalCode(String.valueOf(account.getCustomerId()));

        // Step 3: Merge customer details with account details and return the response
        return new CustomerSummary(
                customerSummary.id(),  // customerId
                customerSummary.name(),
                customerSummary.nationalCode(),
                customerSummary.establishmentDate(),
                customerSummary.customerType(),
                customerSummary.phoneNumber(),
                customerSummary.address(),
                customerSummary.postalCode(),
                account.getAccountType(),
                account.getAccountNumber()
        );
    }

    @Override
    public Account findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    @Override
    @Transactional
    public void update(AccountUpdateRequest accountUpdateRequest) {
        Account prevAccount = findById(accountUpdateRequest.id());
        StringBuilder description = new StringBuilder();
        Optional.ofNullable(accountUpdateRequest.accountType()).ifPresent(newType -> {
            if (!newType.equalsIgnoreCase(prevAccount.getAccountType().name())) {  // .name(): Converts the AccountType enum to its String representation
                description.append("account type: ").append(newType);
                prevAccount.setAccountType(AccountType.valueOf(newType)); // This converts the String to the corresponding AccountType enum.
            }
        });
        if (!description.isEmpty()) {
            description.append(" successfully updated");
            saveHistory(String.valueOf(description), prevAccount);
        }
        repository.save(prevAccount);
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    @Override
    public void pureSave(Account account) {
        repository.save(account);
    }

    private void saveHistory(String description, Account account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "SYSTEM";

        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setUsername(username);
        historyDTO.setRegistrationDate(LocalDateTime.now());
        historyDTO.setDescription(description);
        historyDTO.setAccountId(account.getId());
        historyDTO.setCustomerId(account.getCustomerId()); // Retrieve customerId from the account

        historyEventPublisher.publishHistoryEvent(historyDTO);
    }

    //generated with sequence DB functionality(see in sequence.sql)
    private String generateUniqueAccountNumber() {
        return String.format("%014d", repository.generateAccountNumber());
    } // generates a number(in string format) and pads it with necessary zeros.

}
