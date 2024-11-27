package com.example.customer.repository;

import com.example.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * CustomerRepository is the interface for CRUD operations on Customer entities.
 * This repository extends JpaRepository to provide methods to perform database
 * operations on Customer data.
 *
 * Functionalities provided:
 * - Create, read, update, and delete (CRUD) customers.
 * - Ensure the uniqueness of the customer's DNI.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Checks the existence of a customer by their DNI.
     * This method can be used to enforce unique DNI constraints.
     *
     * @param dni The DNI of the customer to check.
     * @return True if a customer with the specified DNI exists, false otherwise.
     */
    boolean existsByDni(String dni);
}
