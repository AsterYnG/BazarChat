package com.arinc.util.security.tech.filter;

import com.arinc.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@Profile("dev")
@RequiredArgsConstructor
public class AutoAuthFilter extends AbstractAutoAuthFilter {
    private final UserService userService;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        UserDetails user = userService.loadUserByUsername("grinbog015@gmail.com");

        Authentication auth = new TestingAuthenticationToken(user,null,user.getAuthorities());

        // Установка аутентификации в SecurityContext
        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(request, response);
    }
}
