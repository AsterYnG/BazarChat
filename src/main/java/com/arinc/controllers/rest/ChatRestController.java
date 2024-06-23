package com.arinc.controllers.rest;

import com.arinc.dto.MessageCreateDto;
import com.arinc.dto.MessageDto;
import com.arinc.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Slf4j
public class ChatRestController {
    private final ChatService chatService;

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<MessageDto> getLastMessages() {
        log.atTrace().log("Invoked /api/v1/messages/");
        return chatService.getLastMessages();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public List<MessageDto> getLastMessagesFromId(@PathVariable int id) {
        log.atTrace().log("Invoked /api/v1/messages/{id}");
        return chatService.getLastMessagesFromId(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<MessageDto> saveMessage(@RequestBody MessageCreateDto messageCreateDto){
        log.info("Invoked /api/v1/messages/ , Saving message: {} ", messageCreateDto);
        return ResponseEntity.ok(chatService.saveMessage(messageCreateDto));
    }
}
