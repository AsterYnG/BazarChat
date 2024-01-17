package com.arinc.dto;

import jakarta.servlet.http.Part;
import lombok.Builder;
import lombok.Value;

import java.sql.Blob;

@Value
@Builder
public class CustomerRegistrationDto {

    String login;
    String password;
    Part userPic;
}
