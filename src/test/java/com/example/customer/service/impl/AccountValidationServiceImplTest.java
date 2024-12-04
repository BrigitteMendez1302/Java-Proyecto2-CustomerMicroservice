package com.example.customer.service.impl;

import com.example.customer.client.BankAccountClient;
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
        when(bankAccountClient.hasBankAccounts(customerId)).thenReturn(false); // Simulates that it doesn't have active accounts

        // Act
        boolean result = accountValidationService.canDeleteCustomer(customerId);

        // Assert
        assertTrue(result, "Expected true when customer has no active accounts");
    }

    @Test
    void testCanDeleteCustomer_WhenActiveAccountsExist_ShouldReturnFalse() {
        // Arrange
        Long customerId = 1L;
        when(bankAccountClient.hasBankAccounts(customerId)).thenReturn(true); // Simulates that it has active accounts

        // Act
        boolean result = accountValidationService.canDeleteCustomer(customerId);

        // Assert
        assertFalse(result, "Expected false when customer has active accounts");
    }
}
