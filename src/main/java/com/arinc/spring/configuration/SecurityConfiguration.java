package com.arinc.spring.configuration;

import com.arinc.security.service.OidcService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration  {
    private final OidcService oidcService;

//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return web -> web.
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth-> auth.requestMatchers(HttpMethod.GET,
                                        "/login",
                                        "/registration",
                                        "/home",
                                        "/css/**",
                                        "/js/**",
                                        "api/**",
                                        "/error").permitAll()
                                .anyRequest().authenticated()
                        )
                .formLogin(login -> login.loginPage("/login")
                                .defaultSuccessUrl("/home")
                        )
                .logout(logout->
                        logout
                                .logoutUrl("/logout")

                                )
                .httpBasic(Customizer.withDefaults())
                .oauth2Login(oauth->
                        oauth.loginPage("/login")
                                .defaultSuccessUrl("/home")
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.oidcUserService(oidcService))
                )
                .build();
    }



}
