package com.arinc.controllers.rest;

import com.arinc.dto.MessageCreateDto;
import com.arinc.dto.MessageDto;
import com.arinc.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor

public class ChatRestController {
    private final ChatService chatService;

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<MessageDto> getLastMessages() {

        return chatService.getLastMessages();
//        var gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocaDateTimeAdapter());
//        Gson gson = gsonBuilder.create();
//        var json = gson.toJson(messages);
//        writer.write(json);

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Optional<MessageDto> saveMessage(MessageCreateDto messageCreateDto){
        return Optional.of(chatService.saveMessage(messageCreateDto));
    }
//
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        String message1 = req.getParameter("message");
//        CustomerDto user = (CustomerDto) req.getSession().getAttribute("user");
//        MessageDto message = MessageDto.builder()
//                .message(message1)
//                .customerId(user.getId())
//                .build();
//        chatService.saveMessage(message);
//
//    }
}
