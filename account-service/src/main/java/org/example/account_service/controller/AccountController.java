package org.example.account_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.account_service.model.Account;
import org.example.account_service.model.dto.AccountUpdateRequest;
import org.example.account_service.model.dto.CustomerSummary;
import org.example.account_service.service.impl.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@Validated
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Integer> createAccount(@RequestParam Integer customerId) {
        Account account = accountService.createAccount(customerId);

        if (account == null) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if account creation fails
        }

        return ResponseEntity.ok(account.getId()); // Return the account ID
    }

    @GetMapping("/find-account-number/{nationalCode}")
    public JsonMessage<String> findAccountNumber(@PathVariable @NotBlank String nationalCode) {
        return new JsonMessage<>(accountService.findAccountNumberByNationalCode(nationalCode));
    }

    @GetMapping("/get-balance/{accountNumber}")
    public JsonMessage<Double> getBalance(@PathVariable String accountNumber) {
        return new JsonMessage<>(accountService.findBalanceByAccountNumber(accountNumber));
    }

    @GetMapping("/get-summary-customer/{accountNumber}")
    public CustomerSummary getSummaryCustomer(@PathVariable String accountNumber) {
        return accountService.getByAccountNumber(accountNumber);
    }

    @PutMapping("/update")
    public void update(@Valid @RequestBody AccountUpdateRequest accountUpdateRequest) {
        accountService.update(accountUpdateRequest);
    }


    // **** TODO
}
