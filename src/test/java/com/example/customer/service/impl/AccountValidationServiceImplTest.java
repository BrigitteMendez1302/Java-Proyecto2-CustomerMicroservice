package com.example.customer.service.impl;

import com.example.customer.client.BankAccountClient;
import com.example.customer.service.AccountValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class AccountValidationServiceImplTest {

    @Mock
    private BankAccountClient bankAccountClient;

    @InjectMocks
    private AccountValidationServiceImpl accountValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCanDeleteCustomer_WhenNoActiveAccounts_ShouldReturnTrue() {
        // Arrange
        Long customerId = 1L;
        when(bankAccountClient.hasBankAccounts(customerId)).thenReturn(false); // Simula que no tiene cuentas activas

        // Act
        boolean result = accountValidationService.canDeleteCustomer(customerId);

        // Assert
        assertTrue(result, "Expected true when customer has no active accounts");
    }

    @Test
    void testCanDeleteCustomer_WhenActiveAccountsExist_ShouldReturnFalse() {
        // Arrange
        Long customerId = 1L;
        when(bankAccountClient.hasBankAccounts(customerId)).thenReturn(true); // Simula que tiene cuentas activas

        // Act
        boolean result = accountValidationService.canDeleteCustomer(customerId);

        // Assert
        assertFalse(result, "Expected false when customer has active accounts");
    }
}
