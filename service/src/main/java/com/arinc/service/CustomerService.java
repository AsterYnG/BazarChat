package com.arinc.service;

import com.arinc.dao.CustomerDao;
import com.arinc.dto.CustomerDto;
import com.arinc.dto.CustomerRegistrationDto;
import com.arinc.entity.Customer;
import com.arinc.mapper.CustomerMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerService {

    private static final CustomerService INSTANCE = new CustomerService();
    private CustomerService() {}
    public static CustomerService getInstance() {
        return INSTANCE;
    }
    private final CustomerDao customerDao = CustomerDao.getInstance();
    private final CustomerMapper customerMapper = CustomerMapper.getInstance();
    private final ImageService imageService = ImageService.getInstance();

    public Optional<CustomerDto> findUser(String login, String password) {
        var customerEntity = customerDao.findByLoginAndPassword(login, password);
        return customerEntity.map(customerMapper::mapFrom);
    }

    public List<CustomerDto> getOnlineUsers() {
        var onlineUsers = customerDao.getOnlineUsers();
        return onlineUsers.stream()
                .map(customerMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void saveUser(CustomerRegistrationDto customerDto) throws IOException {
        var customer = customerMapper.mapFrom(customerDto);
        imageService.upload(customer.getUserPic(), customerDto.getUserPic().getInputStream());
        customerDao.save(customer);
    }
    public void setOnlineTrue(CustomerDto customerDto) {
        customerDao.setOnlineTrue(customerDto.getId());
    }
    public void setOnlineFalse(CustomerDto customerDto) {
        customerDao.setOnlineFalse(customerDto.getId());
    }
}

