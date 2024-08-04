package com.arinc.security.handler;

import com.arinc.security.jwt.deserializer.JwtTokenDeserializer;
import com.arinc.security.jwt.service.JwtService;
import com.arinc.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final UserService userService;
    private final JwtService jwtService;
    private final JwtTokenDeserializer jwtTokenDeserializer;


    @Value("${bazar.domain.bazar-web}")
    private String bazarWebAddress;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var cookies = request.getCookies();
        //Getting jwt cookie from request, if empty invoke other filters
        if (cookies != null) {
            var maybeCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("jwt-auth-token"))
                    .findFirst();
            maybeCookie.ifPresent(cookie -> {
                var token = jwtTokenDeserializer.deserialize(cookie.getValue());
                userService.setOnline(token.getLogin(), false);
                jwtService.clearCookie(response);
            });
        }
        response.sendRedirect(bazarWebAddress + "/login");
    }
}
