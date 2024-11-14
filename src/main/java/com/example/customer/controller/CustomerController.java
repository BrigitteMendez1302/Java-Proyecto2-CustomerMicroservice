package com.example.customer.controller;

import com.example.customer.model.Customer;
import com.example.customer.service.CustomerService;
import com.example.customer.dto.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * CustomerController handles HTTP requests for managing customers.
 * It provides endpoints for creating, retrieving, updating, and deleting customers.
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  /**
   * Endpoint to create a new customer.
   *
   * @param customer The customer to be created.
   * @return The created customer with status 201 Created.
   */
  @PostMapping
  public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer) {
    Customer createdCustomer = customerService.createCustomer(customer);
    return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
  }

  /**
   * Endpoint to retrieve all customers.
   *
   * @return A list of all customers.
   */
  @GetMapping
  public ResponseEntity<List<Customer>> getAllCustomers() {
    List<Customer> customers = customerService.getAllCustomers();
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  /**
   * Endpoint to retrieve a customer by ID.
   *
   * @param id The ID of the customer to retrieve.
   * @return The customer if found, or an error response if not found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
    Customer customer = customerService.getCustomerById(id)
            .orElseThrow(() -> new NoSuchElementException("Customer not found"));
    return new ResponseEntity<>(customer, HttpStatus.OK);
  }

  /**
   * Endpoint to update a customer's details.
   *
   * @param id       The ID of the customer to update.
   * @param customer The updated customer details.
   * @return The updated customer if successful, or an error response if there is an issue.
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
    Customer updatedCustomer = customerService.updateCustomer(id, customer);
    return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
  }

  /**
   * Endpoint to delete a customer by ID.
   *
   * @param id The ID of the customer to delete.
   * @return 204 No Content if successful, or an error response if there is an issue.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
    boolean isDeleted = customerService.deleteCustomer(id);
    return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
            : new ResponseEntity<>(new ErrorResponse("Customer has active accounts and cannot be deleted", "400"), HttpStatus.BAD_REQUEST);
  }
}
