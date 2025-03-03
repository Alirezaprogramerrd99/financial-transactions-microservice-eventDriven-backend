package org.example.history_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "history")
@ToString

public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "account_id")  // Instead of a reference, store only the account ID
    private Integer accountId;

    @Column(name = "customer_id") // Instead of a reference, store only the customer ID
    private Integer customerId;
}
