package org.example.account_service.dao;

import org.example.account_service.model.Account;
import org.example.account_service.model.dto.CustomerSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query(value = "SELECT nextval('seq_account_number')", nativeQuery = true)
    Long generateAccountNumber();

    @Query("select a from Account a where a.customerId =:customerId")
    Optional<Account> findByCustomerId(@Param("customerId") Integer customerId);

//    @Query("select a from Account a where a.nationalCode =:nationalCode")
//    Optional<Account> findByNationalCode(@Param("nationalCode") String nationalCode);

    @Query("""
            select a.balance from Account a where a.accountNumber =:accountNumber
            """)
    Double findBalanceByAccountNumber(@Param("accountNumber") String accountNumber);


    @Query("""
            select count (a) > 0 from Account a where a.accountNumber =:accountNumber
            """)
    Boolean existByAccountNumber(@Param("accountNumber") String accountNumber);

//    @Query("""
//              select new org.example.financial_transactions.model.dto.CustomerSummary(
//                      c.name,c.nationalCode,c.establishmentDate,c.customerType,
//                      c.phoneNumber,c.address,c.postalCode,a.accountType,
//                      a.accountNumber)
//              from Account a left join a.customer c where a.accountNumber=:accountNumber
//            """)
//    Optional<CustomerSummary> getByAccountNumber(@Param("accountNumber") String accountNumber);

    Optional<Account> findByAccountNumber(String accountNumber);
}
