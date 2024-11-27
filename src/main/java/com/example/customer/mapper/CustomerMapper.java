package com.example.customer.mapper;

import com.example.customer.dto.CustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.model.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerRequest dto) {
        return new Customer(dto.getFirstName(), dto.getLastName(), dto.getDni(), dto.getEmail());
    }

    public static CustomerResponse toResponseDto(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName(),
                customer.getDni(), customer.getEmail());
    }
}
