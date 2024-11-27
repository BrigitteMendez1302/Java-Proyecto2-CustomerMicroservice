package com.example.customer.service.impl;

import com.example.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class DniValidationServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private DniValidationServiceImpl dniValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsUnique_WhenDniDoesNotExist_ShouldReturnTrue() {
        // Arrange
        String dni = "12345678";
        when(customerRepository.existsByDni(dni)).thenReturn(false); // Simula que el DNI no existe

        // Act
        boolean result = dniValidationService.isUnique(dni);

        // Assert
        assertTrue(result, "Expected true when DNI does not exist in the database");
    }

    @Test
    void testIsUnique_WhenDniExists_ShouldReturnFalse() {
        // Arrange
        String dni = "12345678";
        when(customerRepository.existsByDni(dni)).thenReturn(true); // Simula que el DNI ya existe

        // Act
        boolean result = dniValidationService.isUnique(dni);

        // Assert
        assertFalse(result, "Expected false when DNI exists in the database");
    }
}
