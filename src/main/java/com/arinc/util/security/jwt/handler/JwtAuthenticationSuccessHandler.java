package com.arinc.util.security.jwt.handler;

import com.arinc.util.security.jwt.serializer.JwtTokenSerializer;
import com.arinc.util.security.jwt.service.JwtService;
import com.arinc.util.security.jwt.token.JwtToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${bazar.domain.bazar-web}")
    private String bazarWebAddress;
    @Value("${bazar.domain.bazar-web-ip}")
    private String bazarWebIp;

    private final JwtService jwtService;
    private final JwtTokenSerializer jwtTokenSerializer;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(authentication instanceof UsernamePasswordAuthenticationToken){
            JwtToken token = jwtService.generateToken(authentication);
            var serializedToken = jwtTokenSerializer.serialize(token);
            var cookie = new Cookie("jwt-auth-token", serializedToken);
            cookie.setPath("/");
            cookie.setDomain(bazarWebIp);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setMaxAge((int) ChronoUnit.MILLIS.between(Instant.now(), token.getExpiresAt().toInstant()));
//            cookie.setAttribute("SameSite", "None");
            response.addCookie(cookie);
            response.sendRedirect(bazarWebAddress + "/home");
        }
    }
}
