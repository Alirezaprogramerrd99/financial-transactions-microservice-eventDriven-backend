package org.example.customer_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.customer_service.dao.CustomerRepository;
import org.example.customer_service.exception.DuplicateException;
import org.example.customer_service.exception.IdNotFoundException;
import org.example.customer_service.exception.NationalCodeNotFoundException;
import org.example.customer_service.model.Admin;
import org.example.customer_service.model.Customer;
import org.example.customer_service.model.dto.CustomerUpdateRequest;
import org.example.customer_service.model.dto.HistoryDTO;
import org.example.customer_service.model.enumutation.CustomerType;
import org.example.customer_service.service.AccountServiceClient;
import org.example.customer_service.service.ICustomerService;
import org.example.customer_service.event.HistoryEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository repository;
    private final HistoryEventPublisher historyEventPublisher;
    private final AccountServiceClient accountServiceClient;

    @Transactional
    @Override
    public void registerCustomer(Customer customer) {
        Optional<Customer> existingCustomer = repository.findByNationalCode(customer.getNationalCode());
        existingCustomer.ifPresent(c -> {
            throw new DuplicateException(c.getNationalCode());
        });

        // Step 1: Save the customer first without an account ID
        customer = repository.save(customer);

        try {
            // Step 2: Call `account-service` to create an account
            Integer accountId = accountServiceClient.createAccount(customer.getId());

            // Step 3: Update customer with account ID
            customer.setAccountId(accountId);
            repository.save(customer);

            // Step 4: Publish event to `history-service`
            publishHistoryEvent("Customer registered with account ID: " + accountId, customer);
        } catch (Exception e) {
            //  Step 5: Rollback customer creation if account creation fails
            repository.deleteById(customer.getId());
            throw new RuntimeException("Customer registration failed due to account creation error: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Optional<Customer> getCustomerByNationalCode(String nationalCode){
        Optional<Customer> existingCustomer = repository.findByNationalCode(nationalCode);

        if (existingCustomer.isEmpty())
            throw new NationalCodeNotFoundException(nationalCode);

        return existingCustomer;
    }

    @Override
    @Transactional
    public void update(CustomerUpdateRequest customerUpdateRequest) {
        Boolean isDuplicate = findDuplicateByNationalCodeAndId(customerUpdateRequest.nationalCode(), customerUpdateRequest.id());
        if (isDuplicate)
            throw new DuplicateException(customerUpdateRequest.nationalCode());

        Customer prevCustomer = findById(customerUpdateRequest.id());
        StringBuilder description = new StringBuilder();
        Optional.ofNullable(customerUpdateRequest.name()).ifPresent(newName -> {
            if (!newName.equals(prevCustomer.getName())) {
                description.append("name: ").append(newName).append(",");
                prevCustomer.setName(newName);
            }
        });
        Optional.ofNullable(customerUpdateRequest.nationalCode()).ifPresent(newNationalCode -> {
            if (!newNationalCode.equals(prevCustomer.getNationalCode())) {
                description.append("nationalCode: ").append(newNationalCode).append(",");
                prevCustomer.setNationalCode(newNationalCode);
            }
        });
        Optional.ofNullable(customerUpdateRequest.establishmentDate()).ifPresent(newDate -> {
            if (!newDate.equals(prevCustomer.getEstablishmentDate())) {
                description.append("establishmentDate: ").append(newDate).append(",");
                prevCustomer.setEstablishmentDate(newDate);
            }
        });
        Optional.ofNullable(customerUpdateRequest.customerType()).ifPresent(newType -> {
            if (!newType.equals(prevCustomer.getCustomerType().name())) {
                description.append("customerType: ").append(newType).append(",");
                prevCustomer.setCustomerType(CustomerType.valueOf(newType));
            }
        });
        Optional.ofNullable(customerUpdateRequest.phoneNumber()).ifPresent(newPhoneNumber -> {
            if (!newPhoneNumber.equals(prevCustomer.getPhoneNumber())) {
                description.append("phoneNumber: ").append(newPhoneNumber).append(",");
                prevCustomer.setPhoneNumber(newPhoneNumber);
            }
        });
        Optional.ofNullable(customerUpdateRequest.address()).ifPresent(newAddress -> {
            if (!newAddress.equals(prevCustomer.getAddress())) {
                description.append("address: ").append(newAddress).append(",");
                prevCustomer.setAddress(newAddress);
            }
        });
        if (!description.isEmpty()) {
            description.append(" successfully updated");
            publishHistoryEvent(String.valueOf(description), prevCustomer);
        }
        repository.save(prevCustomer);
    }

    @Override
    public Customer findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    private void publishHistoryEvent(String description, Customer customer) {
        Admin principal = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Create a DTO
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setUsername(principal.getUsername());
        historyDTO.setRegistrationDate(LocalDateTime.now());
        historyDTO.setDescription(description);
        historyDTO.setAccountId(customer.getAccountId());
        historyDTO.setCustomerId(customer.getId());

        // Publish event to Kafka
        historyEventPublisher.publishHistoryEvent(historyDTO);
    }

    @Override
    public Boolean findDuplicateByNationalCodeAndId(String nationalCode, Integer id) {
        return repository.findDuplicateByNationalCodeAndId(nationalCode, id);
    }
}

