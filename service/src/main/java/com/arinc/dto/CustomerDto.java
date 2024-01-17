package com.arinc.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerDto {
    Integer id;
    String login;
    String password;
    String userPic;
}
