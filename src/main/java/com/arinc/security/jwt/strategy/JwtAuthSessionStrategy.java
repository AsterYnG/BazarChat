package com.arinc.security.jwt.strategy;

import com.arinc.security.jwt.serializer.JwtTokenSerializer;
import com.arinc.security.jwt.service.JwtService;
import com.arinc.security.jwt.token.JwtToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class JwtAuthSessionStrategy implements SessionAuthenticationStrategy {

    private final JwtService jwtService;
    private final JwtTokenSerializer jwtTokenSerializer;

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws SessionAuthenticationException {
        if(authentication instanceof UsernamePasswordAuthenticationToken){
            JwtToken token = jwtService.generateToken(authentication);
            var serializedToken = jwtTokenSerializer.serialize(token);
            var cookie = new Cookie("jwt-auth-token", serializedToken);
            cookie.setPath("/");
            cookie.setDomain(null);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge((int)ChronoUnit.MILLIS.between(Instant.now(), token.getExpiresAt().toInstant()));

            response.addCookie(cookie);
        }
    }
}
