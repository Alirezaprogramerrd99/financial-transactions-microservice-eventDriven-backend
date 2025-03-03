package org.example.customer_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.customer_service.model.enumutation.CustomerType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
@ToString
@AllArgsConstructor
public class Customer extends User {

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "customer_type", nullable = false)
    private CustomerType customerType;

    // Instead of an Account reference, we store the Account ID
    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    // Remove `@OneToMany` for histories since they are now in `history-service`
    // private List<History> histories;

    public Customer(String name, String nationalCode, LocalDate establishmentDate, String phoneNumber,
                    String address, String postalCode, CustomerType customerType, Integer accountId) {
        super(name, nationalCode, establishmentDate, phoneNumber, address, postalCode);
        this.customerType = customerType;
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // If same object reference, return true
        if (o == null || getClass() != o.getClass()) return false; // Ensure the same class
        Customer customer = (Customer) o;

        // If ID is assigned, use it for comparison
        if (getId() != null && customer.getId() != null) {
            return Objects.equals(getId(), customer.getId());
        }

        // If ID is null (new entity), use business key (e.g., nationalCode)
        return Objects.equals(getNationalCode(), customer.getNationalCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId() != null ? getId() : getNationalCode()); // Use ID if available, otherwise use nationalCode
    }
}
