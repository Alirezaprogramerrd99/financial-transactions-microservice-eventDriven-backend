package org.example.account_service.service;

import org.example.account_service.model.dto.CustomerSummary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

@Service
public class CustomerServiceClient {

    private final RestTemplate restTemplate;

    @Value("${customer-service.url}")
    private String customerServiceUrl;

    public CustomerServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch customer details from customer-service
    public CustomerSummary getCustomerSummaryByNationalCode(String nationalCode) {
        String url = customerServiceUrl + "/api/customers/find-summary/" + nationalCode;

        ResponseEntity<CustomerSummary> response = restTemplate.getForEntity(url, CustomerSummary.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Failed to fetch customer details from customer-service");
        }
        return response.getBody();
    }

    // Check if customer exists by nationalCode
    public boolean existsByNationalCode(String nationalCode) {
        String url = customerServiceUrl + "/api/customers/exists?nationalCode=" + nationalCode;
        return Boolean.TRUE.equals(restTemplate.getForObject(url, Boolean.class));
    }
}