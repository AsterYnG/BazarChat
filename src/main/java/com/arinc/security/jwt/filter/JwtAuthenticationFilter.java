package com.arinc.security.jwt.filter;

import com.arinc.security.jwt.deserializer.JwtTokenDeserializer;
import com.arinc.security.jwt.service.JwtService;
import com.arinc.security.jwt.token.JwtToken;
import com.arinc.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final JwtTokenDeserializer jwtTokenDeserializer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            var cookies = request.getCookies();
            //Getting jwt cookie from request, if empty invoke other filters
            if (cookies == null) {
                doFilter(request, response, filterChain);
                return;
            }
            var maybeCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("jwt-auth-token"))
                    .findFirst();
            if (maybeCookie.isEmpty()) {
                doFilter(request, response, filterChain);
                return;
            }
            JwtToken jwtToken = jwtTokenDeserializer.deserialize(maybeCookie.get().getValue());
            var userDetails = userService.loadUserByUsername(jwtToken.getLogin());

            if (jwtService.validateToken(jwtToken)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        } catch (Exception e) {
            unSuccessAuthentication(request, response, filterChain);
            return;
        }
        doFilter(request, response, filterChain);
    }

    private void unSuccessAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        jwtService.clearCookie(response);
        response.sendRedirect("/login");
    }

}
