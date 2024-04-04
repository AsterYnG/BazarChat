package com.arinc.mapper;

import com.arinc.database.entity.Message;
import com.arinc.database.repository.UserRepository;
import com.arinc.dto.MessageCreateDto;
import com.arinc.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MessageMapper {
    private final UserRepository userRepository;
    public MessageDto mapFrom(Message message){
        return MessageDto.builder()
                .message(message.getMessage())
                .customerId(message.getUser().getId())
                .messageId(message.getId())
                .login(message.getUser().getLogin())
                .date(message.getDate())
                .build();
    }
    public Message mapFrom(MessageCreateDto messageCreateDto){
        return Message.builder()
                .date(LocalDateTime.now())
                .user(userRepository.findById(messageCreateDto.getUserId()).get())
                .message(messageCreateDto.getMessage())
                .build();
    }
}
