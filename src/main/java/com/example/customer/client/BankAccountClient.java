package com.example.customer.client;

import com.example.customer.model.BankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Service class for interacting with the Bank Account microservice.
 * Provides functionality to check if a customer has active bank accounts.
 */
@Service
@RequiredArgsConstructor
public class BankAccountClient {

    private final WebClient webClient; // WebClient used to interact with the Bank Account microservice.

    /**
     * Checks if a customer has active bank accounts by calling the Bank Account microservice.
     *
     * @param customerId The ID of the customer.
     * @return true if the customer has active bank accounts; false otherwise.
     */
    public boolean hasBankAccounts(Long customerId) {
        try {
            // Sends a GET request to the endpoint /api/accounts/customer/{customerId}
            List<?> bankAccounts = webClient.get()
                    .uri("/customer/{customerId}", customerId) // Replace {customerId} with the actual customer ID
                    .retrieve()
                    .bodyToMono(List.class) // Expecting a response body containing a list of bank accounts
                    .block(); // Blocks the reactive flow and retrieves the result synchronously

            // Prints the content of bankAccounts for debugging purposes
            System.out.println("Bank Accounts Response: " + bankAccounts);

            // Checks if the list is not null or empty
            return bankAccounts != null && !bankAccounts.isEmpty();
        } catch (Exception e) {
            // Logs the error and assumes the customer has no bank accounts in case of an exception
            System.err.println("Error checking bank accounts: " + e.getMessage());
            return false;
        }
    }

}
