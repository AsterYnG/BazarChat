package com.arinc.spring.configuration;

import com.arinc.security.handler.CustomLogoutSuccessHandler;
import com.arinc.security.service.OidcService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration  {
    private final OidcService oidcService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return web -> web.
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth-> auth.requestMatchers(
                                        "/login",
                                        "/registration",
                                        "/home",
                                        "/profile",
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
                .build();
    }



}
