package com.arinc.servlets;

import com.arinc.dao.CustomerDao;
import com.arinc.dao.MessageDao;
import com.arinc.dto.MessageDto;
import com.arinc.entity.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.Cleanup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import com.arinc.adapter.LocaDateTimeAdapter;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
    private final MessageDao messageDao = MessageDao.getInstance();

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
        MessageDto message = MessageDto.builder()
                .message(message1)
                .customerId(1)
                .build();
        messageDao.save(message);
    }
}
