package com.example.customer.service.impl;

import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.AccountValidationService;
import com.example.customer.service.DniValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private DniValidationService dniValidationService;

    @Mock
    private AccountValidationService accountValidationService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer_Success() {
        // Arrange
        Customer customer = new Customer("John", "Doe", "12345678", "john.doe@example.com");
        when(dniValidationService.isUnique(customer.getDni())).thenReturn(true);
        when(customerRepository.save(customer)).thenReturn(customer);

        // Act
        Customer result = customerService.createCustomer(customer);

        // Assert
        assertEquals(customer, result);
        verify(customerRepository).save(customer);
    }

    @Test
    void testCreateCustomer_DniNotUnique_ThrowsException() {
        // Arrange
        Customer customer = new Customer("John", "Doe", "12345678", "john.doe@example.com");
        when(dniValidationService.isUnique(customer.getDni())).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(customer));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testGetAllCustomers() {
        // Arrange
        List<Customer> customers = Arrays.asList(
                new Customer("John", "Doe", "12345678", "john.doe@example.com"),
                new Customer("Jane", "Smith", "87654321", "jane.smith@example.com")
        );
        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        List<Customer> result = customerService.getAllCustomers();

        // Assert
        assertEquals(customers, result);
        verify(customerRepository).findAll();
    }

    @Test
    void testGetCustomerById_Found() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer("John", "Doe", "12345678", "john.doe@example.com");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        Optional<Customer> result = customerService.getCustomerById(customerId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
        verify(customerRepository).findById(customerId);
    }

    @Test
    void testGetCustomerById_NotFound() {
        // Arrange
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act
        Optional<Customer> result = customerService.getCustomerById(customerId);

        // Assert
        assertFalse(result.isPresent());
        verify(customerRepository).findById(customerId);
    }

    @Test
    void testUpdateCustomer_Success() {
        // Arrange
        Long customerId = 1L;
        Customer existingCustomer = new Customer("John", "Doe", "12345678", "john.doe@example.com");
        Customer updatedCustomer = new Customer("John", "Doe", "87654321", "john.doe@example.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(dniValidationService.isUnique(updatedCustomer.getDni())).thenReturn(true);
        when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);

        // Act
        Customer result = customerService.updateCustomer(customerId, updatedCustomer);

        // Assert
        assertEquals(existingCustomer, result);
        assertEquals("87654321", result.getDni());
        verify(customerRepository).save(existingCustomer);
    }

    @Test
    void testUpdateCustomer_NotFound_ThrowsException() {
        // Arrange
        Long customerId = 1L;
        Customer updatedCustomer = new Customer("John", "Doe", "87654321", "john.doe@example.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(customerId, updatedCustomer));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomer_Success() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer("John", "Doe", "12345678", "john.doe@example.com");

        when(accountValidationService.canDeleteCustomer(customerId)).thenReturn(true);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        boolean result = customerService.deleteCustomer(customerId);

        // Assert
        assertTrue(result);
        verify(customerRepository).delete(customer);
    }

    @Test
    void testDeleteCustomer_HasActiveAccounts_ThrowsException() {
        // Arrange
        Long customerId = 1L;

        when(accountValidationService.canDeleteCustomer(customerId)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> customerService.deleteCustomer(customerId));
        verify(customerRepository, never()).delete(any(Customer.class));
    }

    @Test
    void testDeleteCustomer_NotFound() {
        // Arrange
        Long customerId = 1L;

        when(accountValidationService.canDeleteCustomer(customerId)).thenReturn(true);
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act
        boolean result = customerService.deleteCustomer(customerId);

        // Assert
        assertFalse(result);
        verify(customerRepository, never()).delete(any(Customer.class));
    }
}
