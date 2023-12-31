package com.arinc.dao;

import com.arinc.entity.Customer;
import com.arinc.util.ConnectionManager;
import com.arinc.util.EntityBuilder;
import lombok.Cleanup;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public void save(Customer entity) {
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

    public List<Customer> getOnlineUsers() {
        String sql = """
                SELECT *
                FROM customer
                WHERE online = true;
                """;
        try (var connection = ConnectionManager.get()) {
           @Cleanup var preparedStatement = connection.prepareStatement(sql);
            var resultSet = preparedStatement.executeQuery();
            List<Customer> result = new ArrayList<>();
            while (resultSet.next()) {
                // TODO Нужно правильно спроектировать базу
                var customer = Customer.builder()
                        .customerId(resultSet.getInt("customer_id"))
                        .login(resultSet.getString("login"))
                        .password(resultSet.getString("password"))
                        .build();
                result.add(customer);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOnlineTrue(int customerId) {
        String sql = """
                UPDATE customer
                SET online = true
                WHERE customer_id = ?;
                """;
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOnlineFalse(int customerId) {
        String sql = """
                UPDATE customer
                SET online = false
                WHERE customer_id = ?;
                """;
        try (var connection = ConnectionManager.get()) {
            @Cleanup var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
