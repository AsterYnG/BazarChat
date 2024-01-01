package com.arinc.service;

import com.arinc.dao.CustomerDao;
import com.arinc.dto.CustomerDto;
import com.arinc.entity.Customer;
import com.arinc.mapper.CustomerMapper;

import java.util.Optional;

public class CustomerService {

    private static final CustomerService INSTANCE = new CustomerService();
    private CustomerService() {}
    public static CustomerService getInstance() {
        return INSTANCE;
    }
    private final CustomerDao customerDao = CustomerDao.getInstance();
    private final CustomerMapper customerMapper = CustomerMapper.getInstance();

    public Optional<CustomerDto> findUser(String login, String password) {
        var customerEntity = customerDao.findByLoginAndPassword(login, password);
        return customerEntity.map(customerMapper::mapFrom);
    }
}

