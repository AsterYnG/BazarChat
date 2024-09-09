package com.arinc.service;

import com.arinc.database.entity.Message;
import com.arinc.database.entity.User;
import com.arinc.database.repository.MessageRepository;
import com.arinc.database.repository.UserRepository;
import com.arinc.dto.MessageCreateDto;
import com.arinc.dto.MessageDto;
import com.arinc.mapper.MessageMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private MessageMapper messageMapper;
    @InjectMocks
    private ChatService chatService;

    private final String message = "Hey";
    private final Integer userId = 1;
    private final Integer messageId = 1;

    @DisplayName("Проверка на сохранение сообщения")
    @Test
    void saveMessage() {
        var messageCreateDto = MessageCreateDto.builder()
                .message(message)
                .userId(userId)
                .build();
        var user = User.builder()
                .id(userId)
                .build();
        var messageEntity = Message.builder()
                .message(message)
                .date(LocalDateTime.now())
                .user(user)
                .build();
        var messageDto = MessageDto.builder()
                .message(message)
                .messageId(messageId)
                .customerId(userId)
                .build();
        var expectedMessageDto = MessageDto.builder()
                .message(message)
                .messageId(messageId)
                .customerId(userId)
                .build();

        when(messageMapper.mapFrom(messageCreateDto)).thenReturn(messageEntity);
        when(messageRepository.saveAndFlush(messageEntity)).thenReturn(messageEntity);
        when(messageMapper.mapFrom(messageEntity)).thenReturn(messageDto);

        MessageDto actualResult = chatService.saveMessage(messageCreateDto);
        assertThat(actualResult).isNotNull()
                .isEqualTo(expectedMessageDto);

    }
    @DisplayName("Проверка на получение сообщений")
    @Test
    void getLastMessages() {
        var user = User.builder()
                .id(userId)
                .build();
        var messageEntity2 = Message.builder()
                .message(message)
                .date(LocalDateTime.now())
                .user(user)
                .id(2)
                .build();
        var messageEntity1 = Message.builder()
                .message(message)
                .date(LocalDateTime.of(2000,1,10,10,20,30))
                .user(user)
                .id(1)
                .build();
        var messageDto1 = MessageDto.builder()
                .message(message)
                .messageId(messageEntity1.getId())
                .customerId(userId)
                .date(messageEntity1.getDate())
                .build();
        var messageDto2 = MessageDto.builder()
                .message(message)
                .messageId(messageEntity2.getId())
                .customerId(userId)
                .date(messageEntity2.getDate())
                .build();
        List<Message> messages = List.of(messageEntity2, messageEntity1);
        List<MessageDto> messagesResult = List.of(messageDto1, messageDto2);

        when(messageRepository.findTop20ByOrderByIdDesc())
                .thenReturn(messages);
        when(messageMapper.mapFrom(messageEntity1)).thenReturn(messageDto1);
        when(messageMapper.mapFrom(messageEntity2)).thenReturn(messageDto2);

        var actualResult = chatService.getLastMessages();

        assertThat(actualResult).isNotNull()
                .isEqualTo(messagesResult);
    }
}