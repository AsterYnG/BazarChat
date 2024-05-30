package com.arinc.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
public class UserOAuthRegistrationDto {
    Integer roleId;
    String login;
    String password;
    String userPic;
}
