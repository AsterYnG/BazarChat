package com.arinc.mapper;

import com.arinc.dto.CustomerDto;
import com.arinc.dto.CustomerRegistrationDto;
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
                .userPic(customer.getUserPic())
                .build();
    }

    public Customer mapFrom(CustomerRegistrationDto customerRegistrationDto) {
        var imageName = customerRegistrationDto.getUserPic().getSubmittedFileName();
        if (imageName.isEmpty()) {
            imageName = "default_user_pic.jpg";
        }
        return Customer.builder()
                .login(customerRegistrationDto.getLogin())
                .password(customerRegistrationDto.getPassword())
                .userPic("userPics/" + imageName)
                .build();
    }
}
