package com.arinc.security.jwt.deserializer;

import com.arinc.security.jwt.token.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenDeserializer {
    @Value("${jwt.secret-token}")
    private String secret;

    public JwtToken deserialize(String token) {
        var parser = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build();
        Claims claims = parser.parseSignedClaims(token)
                .getPayload();
        String login = parser.parseSignedClaims(token)
                .getPayload().getSubject();
        Date issuedAt = parser.parseSignedClaims(token)
                .getPayload().getIssuedAt();
        Date expiresAt = parser.parseSignedClaims(token)
                .getPayload().getExpiration();
        return JwtToken.builder()
                .login(login)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .userId((Integer)claims.get("user_id"))
                .claims(claims)
                .build();
    }
    
}
