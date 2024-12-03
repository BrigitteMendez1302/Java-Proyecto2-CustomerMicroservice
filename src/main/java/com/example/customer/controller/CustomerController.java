package com.example.customer.controller;

import com.example.customer.dto.CustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Customer", description = "Operaciones sobre Customers")
public class CustomerController {

  private final CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Operation(summary = "Create a new customer", description = "Registers a new customer in the system")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Customer created successfully",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
          @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
  })
  @PostMapping
  public ResponseEntity<CustomerResponse> createCustomer(
          @Valid @RequestBody @Parameter(description = "Customer request data", required = true) CustomerRequest customerRequestDto) {
    var customer = customerService.createCustomer(CustomerMapper.toEntity(customerRequestDto));
    return new ResponseEntity<>(CustomerMapper.toResponseDto(customer), HttpStatus.CREATED);
  }

  @Operation(summary = "Get all customers", description = "Retrieve a list of all customers")
  @ApiResponse(responseCode = "200", description = "List of customers",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class)))
  @GetMapping
  public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
    var customers = customerService.getAllCustomers();
    var customerResponseDtos = customers.stream()
            .map(CustomerMapper::toResponseDto)
            .collect(Collectors.toList());
    return new ResponseEntity<>(customerResponseDtos, HttpStatus.OK);
  }

  @Operation(summary = "Get a customer by ID", description = "Retrieve details of a specific customer by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Customer found",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
          @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
  })
  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponse> getCustomerById(
          @PathVariable @Parameter(description = "ID of the customer", required = true) Long id) {
    var customer = customerService.getCustomerById(id)
            .orElseThrow(() -> new NoSuchElementException("Customer not found with ID: " + id));
    return new ResponseEntity<>(CustomerMapper.toResponseDto(customer), HttpStatus.OK);
  }

  @Operation(summary = "Update a customer", description = "Updates the information of an existing customer")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
          @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
          @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
  })
  @PutMapping("/{id}")
  public ResponseEntity<CustomerResponse> updateCustomer(
          @PathVariable @Parameter(description = "ID of the customer", required = true) Long id,
          @Valid @RequestBody @Parameter(description = "Updated customer data", required = true) CustomerRequest customerRequestDto) {
    var updatedCustomer = customerService.updateCustomer(id, CustomerMapper.toEntity(customerRequestDto));
    return new ResponseEntity<>(CustomerMapper.toResponseDto(updatedCustomer), HttpStatus.OK);
  }

  @Operation(summary = "Delete a customer", description = "Deletes a customer by ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
          @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(
          @PathVariable @Parameter(description = "ID of the customer to delete", required = true) Long id) {
    customerService.deleteCustomer(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
