package com.arinc.integration.controllers.rest;

import com.arinc.controllers.rest.ChatRestController;
import com.arinc.dto.MessageCreateDto;
import com.arinc.integration.IntegrationMvcTestBase;
import com.arinc.integration.IntegrationTestBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Path;
import java.time.LocalDateTime;

import static java.nio.file.Files.readString;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RequiredArgsConstructor
class ChatRestControllerTest extends IntegrationMvcTestBase {
    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @Test
    void getLastMessages() throws Exception {
        mvc.perform(get("/api/v1/messages"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(readString(Path.of("src/test/resources/json/getMessages.json"))))
                .andExpect(jsonPath("$[0].message", is("no")));
    }

    @Test
    void saveMessage() throws Exception {

        MessageCreateDto messageCreateDto = MessageCreateDto.builder()
                .message("CREATE MESSAGE")
                .date(LocalDateTime.now())
                .userId(1)
                .build();
        mvc.perform(post("/api/v1/messages").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageCreateDto)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.message", is("CREATE MESSAGE")));
    }
}