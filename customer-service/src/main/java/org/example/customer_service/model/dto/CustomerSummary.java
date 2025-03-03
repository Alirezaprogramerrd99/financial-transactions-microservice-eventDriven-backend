package org.example.customer_service.model.dto;

import org.example.customer_service.model.enumutation.AccountType;
import org.example.customer_service.model.enumutation.CustomerType;

import java.time.LocalDate;

public record CustomerSummary(String name,
                              String nationalCode,
                              LocalDate establishmentDate,
                              CustomerType customerType,
                              String phoneNumber,
                              String address,
                              String postalCode,
                              AccountType accountType,
                              String accountNumber) {
}
