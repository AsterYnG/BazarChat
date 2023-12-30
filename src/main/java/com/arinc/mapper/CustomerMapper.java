package com.arinc.mapper;

import com.arinc.dto.CustomerDto;
import com.arinc.entity.Customer;

public class CustomerMapper {

    private static final CustomerMapper INSTANCE = new CustomerMapper();

    public static CustomerMapper getInstance() {
        return INSTANCE;
    }
    private CustomerMapper() {}
    public CustomerDto mapFrom(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getCustomerId())
                .login(customer.getLogin())
                .password(customer.getPassword())
                .build();
    }
}
