package com.example.customer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * Represents a customer entity with personal details.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "First name is required.")
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @NotBlank(message = "Last name is required.")
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @NotBlank(message = "DNI is required.")
  @Pattern(regexp = "\\d+", message = "DNI must contain only numbers.")
  @Column(name = "dni", nullable = false, unique = true)
  private String dni;

  @NotBlank(message = "Email is required.")
  @Email(message = "Invalid email format.")
  @Column(name = "email", nullable = false)
  private String email;

  /**
   * Default constructor required by JPA.
   */
  public Customer() {
  }

  /**
   * Constructor that initializes a customer with basic information.
   *
   * @param firstName The customer's first name.
   * @param lastName The customer's last name.
   * @param dni The unique identification number for the customer.
   * @param email The customer's email address, with format validation.
   */
  public Customer(String firstName, String lastName, String dni, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.dni = dni;
    this.email = email;
  }
}
