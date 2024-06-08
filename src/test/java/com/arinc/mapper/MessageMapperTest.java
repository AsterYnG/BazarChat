package com.arinc.mapper;

import com.arinc.database.entity.Message;
import com.arinc.database.entity.User;
import com.arinc.database.repository.UserRepository;
import com.arinc.dto.MessageCreateDto;
import com.arinc.dto.MessageDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MessageMapperTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private MessageMapper messageMapper;

    private User user = User.builder()
            .id(1)
            .userPic("PIC")
            .login("login")
            .nickname("Nick")
            .name("NAME")
            .password("pass")
            .role(null)
            .email("mail")
            .surname("surname")
            .online(true)
            .build();
    private final Message message = Message.builder()
            .message("MESSAGE")
            .id(1)
            .date(LocalDateTime.of(2004,3,15,10,15,10))
            .user(user)
            .build();
    private final MessageDto messageDto = MessageDto.builder()
            .messageId(message.getId())
            .customerId(message.getUser().getId())
            .message(message.getMessage())
            .imagePath(message.getUser().getUserPic())
            .date(message.getDate())
            .login(message.getUser().getLogin())
            .nickname(message.getUser().getNickname())
            .build();
    private final MessageCreateDto messageCreateDto = MessageCreateDto.builder()
            .message(message.getMessage())
            .date(message.getDate())
            .build();
    @DisplayName("Проверка на маппинг из Message в MessageDto")
    @Test
    void mapFrom() {

       var result = messageMapper.mapFrom(message);

        assertThat(result).isNotNull().isEqualTo(messageDto);

    }
    @DisplayName("Проверка на маппинг из MessageDto в Message")
    @Test
    void testMapFrom() {
        Mockito.when(userRepository.findById(messageCreateDto.getUserId())).thenReturn(Optional.of(user));
        var result = messageMapper.mapFrom(messageCreateDto);

        var messageWithoutId = Message.builder()
                .message("MESSAGE")
                .date(result.getDate())
                .user(user)
                .build();
        assertThat(result).isNotNull().isEqualTo(messageWithoutId);
    }
}