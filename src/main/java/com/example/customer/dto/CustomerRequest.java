package com.example.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerRequest {

    // Getters y Setters
    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "DNI is required.")
    @Pattern(regexp = "\\d+", message = "DNI must contain only numbers.")
    private String dni;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

}
