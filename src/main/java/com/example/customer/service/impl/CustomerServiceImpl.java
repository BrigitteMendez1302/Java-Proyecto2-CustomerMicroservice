package com.example.customer.service.impl;

import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.AccountValidationService;
import com.example.customer.service.CustomerService;
import com.example.customer.service.DniValidationService;
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

    private final CustomerRepository customerRepository; // Repository for accessing customer data
    private final DniValidationService dniValidationService;
    private final AccountValidationService accountValidationService;
    /**
     * Constructor to initialize CustomerServiceImpl with required dependencies.
     *
     * @param customerRepository Repository for managing customer data.
     */
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, DniValidationService dniValidationService, AccountValidationService accountValidationService) {
        this.customerRepository = customerRepository;
        this.dniValidationService = dniValidationService;
        this.accountValidationService = accountValidationService;
    }

    /**
     * Creates a new customer.
     *
     * @param customer The customer to be created.
     * @return The created customer.
     * @throws IllegalArgumentException If a customer with the same DNI already exists.
     */
    @Override
    public Customer createCustomer(Customer customer) {
        // Check if a customer with the same DNI already exists
        if (!dniValidationService.isUnique(customer.getDni())) {
            throw new IllegalArgumentException("A customer with this DNI already exists."); // Ensure DNI uniqueness
        }
        return customerRepository.save(customer); // Save and return the new customer
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return A list of all customers.
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll(); // Fetch and return all customers
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return An Optional containing the customer if found, or empty if not found.
     */
    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id); // Fetch a customer by their ID
    }

    /**
     * Updates an existing customer.
     *
     * @param id       The ID of the customer to update.
     * @param customer The updated customer details.
     * @return The updated customer.
     * @throws IllegalArgumentException If the customer is not found or if a different customer with the same DNI exists.
     */
    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    // Check if a different customer with the same DNI exists
                    if (!existingCustomer.getDni().equals(customer.getDni()) &&
                            !dniValidationService.isUnique(customer.getDni())) {
                        throw new IllegalArgumentException("A customer with this DNI already exists."); // Ensure DNI uniqueness
                    }
                    // Update customer details
                    existingCustomer.setFirstName(customer.getFirstName());
                    existingCustomer.setLastName(customer.getLastName());
                    existingCustomer.setDni(customer.getDni());
                    existingCustomer.setEmail(customer.getEmail());
                    return customerRepository.save(existingCustomer); // Save updated customer
                })
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + id)); // Handle customer not found
    }


    /**
     * Deletes a customer if they have no active bank accounts.
     *
     * @param id The ID of the customer to delete.
     * @return true if the customer was deleted; false otherwise.
     * @throws IllegalStateException If the customer has active bank accounts.
     */
    @Override
    public boolean deleteCustomer(Long id) {
        if (!accountValidationService.canDeleteCustomer(id)) {
            throw new IllegalStateException("Customer has active bank accounts and cannot be deleted."); // Ensure no active bank accounts
        }
        return customerRepository.findById(id)
                .map(customer -> {
                    customerRepository.delete(customer); // Delete the customer
                    return true; // Return success
                })
                .orElse(false); // Return false if the customer is not found
    }
}
