package com.arinc.dao;

import com.arinc.entity.Message;
import com.arinc.util.ConnectionManager;
import lombok.Cleanup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.arinc.builder.EntityBuilder.buildMessage;

public class MessageDao implements Dao<Integer, Message> {
    private static final MessageDao INSTANCE = new MessageDao();

    public static MessageDao getInstance() {
        return INSTANCE;
    }

    private MessageDao() {
    }

    private final String SELECT_LAST = """
            SELECT * FROM (
            SELECT *
            FROM message
            JOIN public.customer c on c.customer_id = message.customer_id
            JOIN public.role r on r.role_id = c.role_id
            ORDER BY date DESC
            LIMIT 10) AS TEMP
            ORDER BY date
            """;

    private final String SAVE = """
            INSERT INTO message(message, customer_id, date) VALUES (?,?, now());
            """;


    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    public Optional<Message> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Message update(Message entity) {
        return null;
    }

    @Override
    public void save(Message entity) {
        try(var connection = ConnectionManager.get()) {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SAVE);
            preparedStatement.setObject(1, entity.getMessage());
            preparedStatement.setObject(2, entity.getCustomer().getCustomerId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Message> findLastMessages() {
      try(var connection = ConnectionManager.get()) {
          @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LAST);
          ResultSet resultSet = preparedStatement.executeQuery();
          List<Message> result = new ArrayList<>();
          while (resultSet.next()){
              result.add(buildMessage(resultSet));
          }
          return result;
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }

    }
}


