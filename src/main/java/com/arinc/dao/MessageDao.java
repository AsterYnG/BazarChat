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

import static com.arinc.util.EntityBuilder.buildMessage;

public class MessageDao implements Dao<Integer, Message> {
    private final MessageDao INSTANCE = new MessageDao();

    public MessageDao getInstance() {
        return INSTANCE;
    }

    private MessageDao() {
    }

    private final String SELECT_LAST = """
            SELECT * FROM last_messages();
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
    public Message save(Message entity) {
        return null;
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


