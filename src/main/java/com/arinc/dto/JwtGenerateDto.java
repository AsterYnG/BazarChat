package com.arinc.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class JwtGenerateDto {
    String login;
    String password;
}
