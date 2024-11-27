package com.example.customer.service;

public interface DniValidationService {
    /**
     * Checks if a DNI is unique in the system.
     *
     * @param dni The DNI to validate.
     * @return True if the DNI is unique, false otherwise.
     */
    boolean isUnique(String dni);
}
