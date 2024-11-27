package com.example.customer.service.impl;

import com.example.customer.client.BankAccountClient;
import com.example.customer.service.AccountValidationService;
import org.springframework.stereotype.Service;

@Service
public class AccountValidationServiceImpl implements AccountValidationService {

    private final BankAccountClient bankAccountClient;

    public AccountValidationServiceImpl(BankAccountClient bankAccountClient) {
        this.bankAccountClient = bankAccountClient;
    }

    @Override
    public boolean canDeleteCustomer(Long customerId) {
        return !bankAccountClient.hasBankAccounts(customerId);
    }
}

