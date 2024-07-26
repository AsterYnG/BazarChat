package com.arinc.security.jwt.serializer;

import com.arinc.security.jwt.token.JwtToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenSerializer {
    @Value("${jwt.secret-token}")
    private String secret;
    @Value("${jwt.token-expiration}")
    private Integer tokenExpiration;


    public String serialize(JwtToken jwtToken) {
        return Jwts.builder()
                .claims()
                .add(jwtToken.getClaims())
                .and()
                .subject(jwtToken.getLogin())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(jwtToken.getExpiresAt())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

}
