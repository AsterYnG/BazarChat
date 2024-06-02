package com.arinc.spring.configuration;

import com.arinc.security.handler.CustomLogoutSuccessHandler;
import com.arinc.security.service.OidcService;
import com.arinc.security.tech.filter.AbstractAutoAuthFilter;
import com.arinc.security.tech.filter.AutoAuthFilter;
import com.arinc.security.tech.filter.MockFilter;
import com.arinc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration  {
    private final OidcService oidcService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final AbstractAutoAuthFilter autoAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth-> auth.requestMatchers(
                                        "/login",
                                        "/registration",
                                        "/home",
                                        "/*.png",
                                        "/*.jpg",
                                        "/profile",
                                        "/profile/**",
                                        "/css/**",
                                        "/js/**",
                                        "api/**",
                                        "/error").permitAll()
                                .anyRequest().authenticated()
                        )
                .formLogin(login -> login.loginPage("/login")
                                .successHandler(authenticationSuccessHandler)
                        )
                .logout(logout->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessHandler(customLogoutSuccessHandler)
                                )
                .httpBasic(Customizer.withDefaults())
                .oauth2Login(oauth->
                        oauth.loginPage("/login")
                                .defaultSuccessUrl("/home")
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.oidcUserService(oidcService))
                                .successHandler(authenticationSuccessHandler)
                )
                .addFilterBefore(autoAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }






}
