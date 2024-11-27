package com.example.customer.service;

public interface AccountValidationService {
    /**
     * Checks if a customer can be deleted based on their active bank accounts.
     *
     * @param customerId The ID of the customer.
     * @return True if the customer can be deleted, false otherwise.
     */
    boolean canDeleteCustomer(Long customerId);
}
