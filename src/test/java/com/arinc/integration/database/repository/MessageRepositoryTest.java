package com.arinc.integration.database.repository;

import com.arinc.database.entity.Message;
import com.arinc.database.repository.MessageRepository;
import com.arinc.integration.IntegrationTestBase;
import com.arinc.integration.annotation.IntegrationTest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;


@RequiredArgsConstructor()
class MessageRepositoryTest extends IntegrationTestBase {

    private final MessageRepository messageRepository;

    @Test
    void findTop10ByOrderByIdDesc() {
        List<Message> messages = messageRepository.findTop20ByOrderByIdDesc();
        Assertions.assertThat(messages).isNotEmpty()
                .hasSize(10);
    }
}