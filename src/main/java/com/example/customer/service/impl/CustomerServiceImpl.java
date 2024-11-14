package com.example.customer.service.impl;

import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * CustomerServiceImpl provides the implementation of the business logic for managing customers.
 * It performs CRUD operations and ensures the uniqueness of the customer's DNI.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        // Check if a customer with the same DNI already exists
        if (customerRepository.existsByDni(customer.getDni())) {
            throw new IllegalArgumentException("A customer with this DNI already exists.");
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    // Check if a different customer with the same DNI exists
                    if (!existingCustomer.getDni().equals(customer.getDni()) &&
                            customerRepository.existsByDni(customer.getDni())) {
                        throw new IllegalArgumentException("A customer with this DNI already exists.");
                    }
                    existingCustomer.setFirstName(customer.getFirstName());
                    existingCustomer.setLastName(customer.getLastName());
                    existingCustomer.setDni(customer.getDni());
                    existingCustomer.setEmail(customer.getEmail());
                    return customerRepository.save(existingCustomer);
                })
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + id));
    }

    @Override
    public boolean deleteCustomer(Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customerRepository.delete(customer);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean existsByDni(String dni) {
        return customerRepository.existsByDni(dni);
    }
}
