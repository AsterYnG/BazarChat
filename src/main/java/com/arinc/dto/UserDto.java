package com.arinc.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    Integer id;
    String login;
    String password;
    String userPic;
    String role;
}
