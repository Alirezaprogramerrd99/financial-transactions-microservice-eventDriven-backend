package org.example.customer_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.customer_service.mapstruct.CustomerMapper;
import org.example.customer_service.model.Customer;
import org.example.customer_service.model.dto.CustomerRequest;
import org.example.customer_service.model.dto.CustomerUpdateRequest;
import org.example.customer_service.service.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody CustomerRequest customerRequest) {
        Customer customer = CustomerMapper.INSTANCE.customerRequestToCustomer(customerRequest);
        customerService.registerCustomer(customer);
    }

    @GetMapping("/get/bycode")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<Customer> getCustomerByID(@Valid @RequestBody CustomerRequest customerRequest){
        // the check code should be written here...
        return customerService.getCustomerByNationalCode(customerRequest.nationalCode());
    }

    @PutMapping("/update")
    public void update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.update(customerUpdateRequest);
    }
}
