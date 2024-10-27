package com.arinc.util.security.jwt.token;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.Map;

@Builder
@Value
public class JwtToken {
    String login;
    Integer userId;
    Date expiresAt;
    Date issuedAt;
    Claims claims;
}
