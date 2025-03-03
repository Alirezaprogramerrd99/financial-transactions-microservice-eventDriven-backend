package org.example.customer_service.dao;

import jakarta.validation.constraints.NotNull;
import org.example.customer_service.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByNationalCode(@NotNull String nationalCode);

    @Query("""
            select count(c) > 0 from Customer c where c.nationalCode = :nationalCode and c.id<>:id
            """)
    Boolean findDuplicateByNationalCodeAndId(@Param("nationalCode") String nationalCode, @Param("id") Integer id);
}
