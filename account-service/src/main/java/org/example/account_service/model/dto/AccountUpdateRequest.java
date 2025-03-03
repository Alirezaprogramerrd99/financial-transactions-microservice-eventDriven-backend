package org.example.account_service.model.dto;

import org.example.account_service.validation.AccountType.AccountTypeValid;

public record AccountUpdateRequest(Integer id,
                                   @AccountTypeValid String accountType) {
}
