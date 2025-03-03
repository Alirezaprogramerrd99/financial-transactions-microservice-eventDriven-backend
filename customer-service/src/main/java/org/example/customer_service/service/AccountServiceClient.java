package org.example.customer_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


// sends REST http requests to the account service.
@Service
public class AccountServiceClient {

    private final RestTemplate restTemplate;

    @Value("${account-service.url}")
    private String accountServiceUrl;

    public AccountServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Integer createAccount(Integer customerId) {
        String url = accountServiceUrl + "/api/accounts/create?customerId=" + customerId;
        return restTemplate.postForObject(url, null, Integer.class); // Returns accountId
    }

    // Method to check if an account exists by account number
    public boolean existsByNationalCode(String nationalCode) {
        String url = accountServiceUrl + "/api/accounts/exists?nationalCode=" + nationalCode;
        return restTemplate.getForObject(url, Boolean.class);
    }

    // Method to create an admin account
    public void createAdminAccount(String nationalCode) {
        String url = accountServiceUrl + "/api/accounts/create-admin?nationalCode=" + nationalCode;
        restTemplate.postForLocation(url, null);
    }
}