package org.example.customer_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ErrorDetails {
    private LocalDateTime localDateTime;
    private String massage;
}