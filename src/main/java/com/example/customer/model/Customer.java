package com.example.customer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;

/**
 * Represents a customer entity with personal details.
 */
@Entity
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

  // Getters y Setters

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Provides a string representation of the customer's key details.
   *
   * @return A string with the customer's main information.
   */
  @Override
  public String toString() {
    return "Customer{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", dni='" + dni + '\'' +
            ", email='" + email + '\'' +
            '}';
  }

  /**
   * Compares this customer with another object for equality based on ID.
   *
   * @param o the object to compare.
   * @return true if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;
    return Objects.equals(id, customer.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
