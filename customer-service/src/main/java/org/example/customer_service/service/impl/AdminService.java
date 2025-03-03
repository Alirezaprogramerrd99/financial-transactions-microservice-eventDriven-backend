package org.example.customer_service.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.customer_service.dao.AdminRepository;
import org.example.customer_service.exception.DuplicateException;
import org.example.customer_service.model.Admin;
import org.example.customer_service.model.enumutation.Role;

import org.example.customer_service.service.IAdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.example.customer_service.service.AccountServiceClient;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {
    private final AdminRepository repository;
    private final AccountServiceClient accountServiceClient;
    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    public void init() {
        registerAdmin();
        registerBankAccount();
    }

    public void registerAdmin() {
        Optional<Admin> byNationalCode = repository.findByNationalCode("1111111111");
        if (byNationalCode.isEmpty()) {
            Admin admin = new Admin();
            admin.setNationalCode("1111111111");
            admin.setPassword(passwordEncoder.encode("22222222"));
            admin.setName("bank employee");
            admin.setRole(Role.ROLE_EMPLOYEE);
            admin.setAddress("tehran,golgasht ");
            admin.setPhoneNumber("09919855454");
            admin.setEstablishmentDate(LocalDate.now());
            admin.setPostalCode("7745874787");
            repository.save(admin);
        }
    }

    public void registerBankAccount() {
        String adminNationalCode = "1111111111";

        // Step 1: Check if the admin account already exists in account-service
        boolean exists = accountServiceClient.existsByNationalCode(adminNationalCode);

        if (!exists) {
            // Step 2: Create the admin account in account-service
            accountServiceClient.createAdminAccount(adminNationalCode);
            System.out.println("Admin bank account created.");
        } else {
            System.out.println("Admin bank account already exists.");
            throw new DuplicateException(adminNationalCode);
        }
    }
}
