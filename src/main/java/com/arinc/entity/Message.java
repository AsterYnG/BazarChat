package com.arinc.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Message {
    private Integer messageId;
    private Customer customerId;
    private LocalDateTime date;
    private String message;
}
