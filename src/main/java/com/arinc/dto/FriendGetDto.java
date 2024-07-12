package com.arinc.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class FriendGetDto {
    Integer id;
    UserDto owner;
    UserDto friend;
    LocalDateTime submittedDate;
    String status;
}
