package com.example.customer.service;

import com.example.customer.model.Customer;

import java.util.List;
import java.util.Optional;

/**
 * CustomerService defines the business operations for managing customers.
 * It includes methods for CRUD operations and custom checks.
 */
public interface CustomerService {

  /**
   * Creates a new customer.
   *
   * @param customer The customer to be created.
   * @return The created customer.
   */
  Customer createCustomer(Customer customer);

  /**
   * Retrieves all customers.
   *
   * @return A list of all customers.
   */
  List<Customer> getAllCustomers();

  /**
   * Retrieves a customer by their ID.
   *
   * @param id The ID of the customer to retrieve.
   * @return An Optional containing the customer if found, or empty if not found.
   */
  Optional<Customer> getCustomerById(Long id);

  /**
   * Updates an existing customer's details.
   *
   * @param id The ID of the customer to update.
   * @param customer The customer details to update.
   * @return The updated customer.
   * @throws IllegalArgumentException if the customer does not exist.
   */
  Customer updateCustomer(Long id, Customer customer);

  /**
   * Deletes a customer by their ID.
   *
   * @param id The ID of the customer to delete.
   * @return True if the customer was successfully deleted, false otherwise.
   */
  boolean deleteCustomer(Long id);
}