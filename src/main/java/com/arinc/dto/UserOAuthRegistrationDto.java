package com.arinc.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserOAuthRegistrationDto {
    Integer roleId;
    String email;
    String login;
    String password;
    String userPic;
}
