package com.arinc.security.jwt.service;


import com.arinc.database.repository.UserRepository;
import com.arinc.security.jwt.deserializer.JwtTokenDeserializer;
import com.arinc.security.jwt.serializer.JwtTokenSerializer;
import com.arinc.security.jwt.token.JwtToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {
    @Value("${jwt.token-expiration}")
    private Integer tokenExpiration;

    private final UserRepository userRepository;

    public JwtToken generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        var user = userRepository.findByLogin(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        claims.put("user_id", user.getId());
        claims.put("login", user.getLogin());
        claims.put("role", user.getRole().getRoleName());
        return JwtToken.builder()
                .login(user.getLogin())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiresAt(new Date(System.currentTimeMillis() + tokenExpiration * 1000 * 60 * 60))
                .claims(new DefaultClaims(claims))
                .userId(user.getId())
                .build();
    }


    public boolean validateToken(JwtToken token) {
        return !isTokenExpired(token);
    }

    public boolean isTokenExpired(JwtToken token) {
        return token.getExpiresAt().before(new Date());
    }

    public void clearCookie(HttpServletResponse response) {

        var cookie = new Cookie("jwt-auth-token", null);
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

}
