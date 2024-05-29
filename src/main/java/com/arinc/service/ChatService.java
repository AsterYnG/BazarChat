package com.arinc.service;

import com.arinc.database.repository.UserRepository;
import com.arinc.database.repository.MessageRepository;
import com.arinc.dto.MessageCreateDto;
import com.arinc.dto.MessageDto;
import com.arinc.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PreAuthorize("isAuthenticated()")
    public MessageDto saveMessage(MessageCreateDto messageCreateDto){
       return Optional.of(messageCreateDto)
                .map(messageMapper::mapFrom)
                .map(messageRepository::saveAndFlush)
               .map(messageMapper::mapFrom)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @PreAuthorize("permitAll()")
    public List<MessageDto> getLastMessages(){
       return messageRepository.findTop10ByOrderByIdDesc().stream()
                .map(messageMapper::mapFrom)
               .sorted(Comparator.reverseOrder())
                .toList();
    }

}