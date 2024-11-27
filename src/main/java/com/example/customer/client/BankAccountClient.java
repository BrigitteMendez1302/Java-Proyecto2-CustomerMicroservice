package com.example.customer.client;

import com.example.customer.model.BankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountClient {

    private final WebClient webClient; // WebClient utilizado para interactuar con el microservicio de cuentas bancarias.

    /**
     * Verifica si un cliente tiene cuentas bancarias activas.
     *
     * @param customerId El ID del cliente.
     * @return true si el cliente tiene cuentas bancarias activas; false si no las tiene.
     */
    public boolean hasBankAccounts(Long customerId) {
        try {
            // Llama al endpoint /api/accounts/customer/{customerId}
            List<?> bankAccounts = webClient.get()
                    .uri("/customer/{customerId}", customerId) // Llama al endpoint /api/accounts/customer/{customerId}
                    .retrieve()
                    .bodyToMono(List.class) // Espera recibir una lista de cuentas bancarias
                    .block(); // Bloquea el flujo y obtiene el resultado sincrónicamente

            // Imprime el contenido de bankAccounts
            System.out.println("Bank Accounts Response: " + bankAccounts);

            // Verifica si la lista no está vacía
            return bankAccounts != null && !bankAccounts.isEmpty();
        } catch (Exception e) {
            System.err.println("Error checking bank accounts: " + e.getMessage());
            return false; // En caso de error, asumimos que no tiene cuentas bancarias
        }
    }

}

