package com.arinc.config;

import com.arinc.util.security.exceptions.handler.CustomAuthExceptionHandler;
import com.arinc.util.security.handler.AuthFailureHandler;
import com.arinc.util.security.handler.AuthSuccessHandler;
import com.arinc.util.security.handler.CustomLogoutSuccessHandler;
import com.arinc.util.security.jwt.filter.JwtAuthenticationFilter;
import com.arinc.util.security.jwt.handler.JwtAuthenticationSuccessHandler;
import com.arinc.util.security.service.OidcService;
import com.arinc.util.security.tech.filter.AbstractAutoAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@Profile("!test")
public class SecurityConfiguration {
    private final OidcService oidcService;
    private final AuthSuccessHandler authenticationSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final AbstractAutoAuthFilter autoAuthFilter;
    private final CustomAuthExceptionHandler authEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final AuthFailureHandler authFailureHandler;

    @Value("${bazar.domain.bazar-web}")
    private String bazarWebAddress;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                                "/login",
                                "/registration",
                                "/home",
                                "/*.png",
                                "/*.jpg",
                                "/profile",
                                "/profile/**",
                                "/api/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/global",
                                "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login.
                        defaultSuccessUrl(bazarWebAddress + "/home")
                        .successHandler(jwtAuthenticationSuccessHandler)
                        .failureHandler(authFailureHandler)
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessHandler(customLogoutSuccessHandler)
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth ->
                        oauth.loginPage("/login")
                                .defaultSuccessUrl(bazarWebAddress + "/home")
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.oidcUserService(oidcService))
                                .successHandler(authenticationSuccessHandler)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
                .addFilterBefore(autoAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
