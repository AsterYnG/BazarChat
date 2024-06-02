package com.arinc.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Arrays;

@Value
@Builder
public class MessageDto implements Comparable<MessageDto> {
    String message;
    Integer messageId;
    Integer customerId;
    String login;
    String nickname;
    LocalDateTime date;
    String imagePath;

    @Override
    public int compareTo(MessageDto nextDto) {
        if (nextDto.getMessageId() > this.getMessageId()) return 1;
        if (nextDto.getMessageId() < this.messageId) return -1;
        else return 0;
    }
}
