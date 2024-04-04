package com.arinc.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
public class UserRegistrationDto {

    @NotBlank
    String login;
    String password;
    MultipartFile userPic;
}
