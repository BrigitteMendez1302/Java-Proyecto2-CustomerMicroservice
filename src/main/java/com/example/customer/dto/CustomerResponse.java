package com.example.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String dni;
    private String email;

    public CustomerResponse(Long id, String firstName, String lastName, String dni, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
    }

}
