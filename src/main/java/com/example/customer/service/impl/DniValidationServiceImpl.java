package com.example.customer.service.impl;

import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.DniValidationService;
import org.springframework.stereotype.Service;

@Service
public class DniValidationServiceImpl implements DniValidationService {

    private final CustomerRepository customerRepository;

    public DniValidationServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean isUnique(String dni) {
        return !customerRepository.existsByDni(dni);
    }
}
