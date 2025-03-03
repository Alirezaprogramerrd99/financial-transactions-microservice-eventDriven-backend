package org.example.customer_service.service;

import org.example.customer_service.model.Customer;
import org.example.customer_service.model.dto.CustomerUpdateRequest;

import java.util.Optional;

public interface ICustomerService {

    void registerCustomer(Customer customer);

    public Optional<Customer> getCustomerByNationalCode(String nationalCode);

    void update(CustomerUpdateRequest customerUpdateRequest);

    Customer findById(Integer id);

    Boolean findDuplicateByNationalCodeAndId(String nationalCode, Integer id);
}
