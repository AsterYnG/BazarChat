package com.arinc.util;

import com.arinc.entity.Customer;
import com.arinc.entity.Message;
import com.arinc.entity.Role;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@UtilityClass
public class EntityBuilder {
    public static Message buildMessage(ResultSet resultSet) throws SQLException {
        return Message.builder()
                .message(resultSet.getObject("message",String.class))
                .date(resultSet.getObject("date", LocalDateTime.class))
                .messageId(resultSet.getObject("message_id", Integer.class))
                .customer(buildCustomer(resultSet))
                .build();
    }

    public static Customer buildCustomer(ResultSet resultSet) throws SQLException {
        return Customer.builder()
                .customerId(resultSet.getObject("customer_id", Integer.class))
                .login(resultSet.getObject("login", String.class))
                .password(resultSet.getObject("password", String.class))
                .userPic(resultSet.getString("user_pic"))
                .build();
    }

   public static Role buildRole(ResultSet resultSet) throws SQLException {
        return Role.builder()
                .roleName(resultSet.getObject("role_name",String.class))
                .roleId(resultSet.getObject("role_id",Integer.class))
                .build();
    }
}
