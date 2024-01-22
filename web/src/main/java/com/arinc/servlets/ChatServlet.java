package com.arinc.servlets;

import com.arinc.dao.MessageDao;
import com.arinc.dto.CustomerDto;
import com.arinc.dto.MessageDto;
import com.arinc.service.ChatService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Cleanup;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import com.arinc.adapter.LocaDateTimeAdapter;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
    private final MessageDao messageDao = MessageDao.getInstance();
    private final ChatService chatService = ChatService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @Cleanup PrintWriter writer = resp.getWriter();
        var messages = messageDao.findLastMessages();
        var gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocaDateTimeAdapter());
        Gson gson = gsonBuilder.create();
        var json = gson.toJson(messages);
        writer.write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String message1 = req.getParameter("message");
        CustomerDto user = (CustomerDto) req.getSession().getAttribute("user");
        MessageDto message = MessageDto.builder()
                .message(message1)
                .customerId(user.getId())
                .build();
        chatService.saveMessage(message);

    }
}
