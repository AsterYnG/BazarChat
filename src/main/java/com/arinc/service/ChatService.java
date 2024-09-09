package com.arinc.service;

import com.arinc.database.repository.UserRepository;
import com.arinc.database.repository.MessageRepository;
import com.arinc.dto.MessageCreateDto;
import com.arinc.dto.MessageDto;
import com.arinc.mapper.MessageMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;
    @PreAuthorize("isAuthenticated()")
    public MessageDto saveMessage(MessageCreateDto messageCreateDto) {
        return Optional.of(messageCreateDto)
                .map(messageMapper::mapFrom)
                .map(messageRepository::saveAndFlush)
                .map(messageMapper::mapFrom)
                .map(message -> {
                    messagingTemplate.convertAndSend("/topic/messages", message);
                    return message;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @PreAuthorize("permitAll()")
    public List<MessageDto> getLastMessages() {
        return messageRepository.findTop20ByOrderByIdDesc().stream()
                .map(messageMapper::mapFrom)
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    public List<MessageDto> getLastMessagesFromId(int id) {
        int idMin;
        if (id < 10){
            idMin = 1;
        }
        else idMin = id - 10;
        return messageRepository.findMessagesByIdBetween(idMin,id)
                .stream()
                .map(messageMapper::mapFrom)
                .sorted(Comparator.naturalOrder())
                .toList();
    }
}
