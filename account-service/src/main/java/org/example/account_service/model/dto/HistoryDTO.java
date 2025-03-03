package org.example.account_service.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistoryDTO {
    private String username;
    private LocalDateTime registrationDate;
    private String description;
    private Integer customerId;  //Always null for account-related history
    private Integer accountId;
}