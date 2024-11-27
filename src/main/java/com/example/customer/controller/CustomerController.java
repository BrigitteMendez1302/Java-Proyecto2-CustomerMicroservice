package com.example.customer.controller;

import com.example.customer.dto.CustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * CustomerController handles HTTP requests for managing customers.
 * It provides endpoints for creating, retrieving, updating, and deleting customers.
 *
 * Uses DTOs to encapsulate input and output data, separating API representation
 * from the internal persistence model.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  private final CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  /**
   * Creates a new customer.
   *
   * @param customerRequestDto The DTO containing customer data to create.
   * @return The created customer's data wrapped in a CustomerResponseDto with HTTP 201 status.
   */
  @PostMapping
  public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequestDto) {
    var customer = customerService.createCustomer(CustomerMapper.toEntity(customerRequestDto));
    return new ResponseEntity<>(CustomerMapper.toResponseDto(customer), HttpStatus.CREATED);
  }

  /**
   * Retrieves all customers.
   *
   * @return A list of CustomerResponseDto representing all customers with HTTP 200 status.
   */
  @GetMapping
  public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
    var customers = customerService.getAllCustomers();
    var customerResponseDtos = customers.stream()
            .map(CustomerMapper::toResponseDto)
            .collect(Collectors.toList());
    return new ResponseEntity<>(customerResponseDtos, HttpStatus.OK);
  }

  /**
   * Retrieves a specific customer by ID.
   *
   * @param id The ID of the customer to retrieve.
   * @return The customer's data wrapped in a CustomerResponseDto with HTTP 200 status.
   * @throws NoSuchElementException if the customer is not found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
    var customer = customerService.getCustomerById(id)
            .orElseThrow(() -> new NoSuchElementException("Customer not found with ID: " + id));
    return new ResponseEntity<>(CustomerMapper.toResponseDto(customer), HttpStatus.OK);
  }

  /**
   * Updates an existing customer.
   *
   * @param id The ID of the customer to update.
   * @param customerRequestDto The DTO containing updated customer data.
   * @return The updated customer's data wrapped in a CustomerResponseDto with HTTP 200 status.
   * @throws IllegalArgumentException if the customer does not exist or data is invalid.
   */
  @PutMapping("/{id}")
  public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id,
                                                            @Valid @RequestBody CustomerRequest customerRequestDto) {
    var updatedCustomer = customerService.updateCustomer(id, CustomerMapper.toEntity(customerRequestDto));
    return new ResponseEntity<>(CustomerMapper.toResponseDto(updatedCustomer), HttpStatus.OK);
  }

  /**
   * Deletes a customer by ID.
   *
   * @param id The ID of the customer to delete.
   * @return HTTP 204 No Content if the deletion is successful.
   * @throws IllegalStateException if the customer has active bank accounts.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomer(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
