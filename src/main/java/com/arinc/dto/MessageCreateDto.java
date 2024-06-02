package com.arinc.dto;

import lombok.*;

import java.time.LocalDateTime;

@Value
@Builder
@ToString
public class MessageCreateDto {
    String message;
    Integer userId;
    LocalDateTime date;

}
