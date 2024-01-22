package com.arinc.service;

import com.arinc.dao.CustomerDao;
import com.arinc.dto.MessageDto;
import com.arinc.entity.Message;

public class ChatService {
    private final CustomerDao customerDao = CustomerDao.getInstance();
    private static final ChatService INSTANCE = new ChatService();

    public static ChatService getInstance() {
        return INSTANCE;
    }

    public boolean saveMessage(MessageDto messageDto){
        Message.builder()
                .message(messageDto.getMessage())
                .customer(customerDao.findById(messageDto.getCustomerId()).get())
                .build();
        return true;
    }

}
