package org.example.account_service.validation.AccountType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class AccountTypeValidator implements ConstraintValidator<AccountTypeValid, String> {
    //**** elements in the following arraylist must be lowercase.
    private static final List<String> VALID_COLUMN_SEARCH = Arrays.asList("active","inactive","blocked");
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return name == null || VALID_COLUMN_SEARCH.contains(name.toLowerCase());
    }
}