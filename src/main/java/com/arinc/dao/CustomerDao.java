package com.arinc.dao;

import com.arinc.entity.Customer;
import com.arinc.util.ConnectionManager;
import com.arinc.util.EntityBuilder;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerDao implements Dao<Integer, Customer> {

    private static CustomerDao INSTANCE = new CustomerDao();

    public static CustomerDao getInstance() {
        return INSTANCE;
    }

    private CustomerDao() {}

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Customer update(Customer entity) {
        return null;
    }

    @Override
    public Customer save(Customer entity) {
        return null;
    }

    public Optional<Customer> findByLoginAndPassword(String login, String password) {
        String sql = """
                SELECT *
                FROM customer
                WHERE login = ? AND password = ?;
                """;
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(EntityBuilder.buildCustomer(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
