package com.arinc.controllers.rest;

import com.arinc.dto.UserDto;
import com.arinc.dto.UserRegistrationDto;
import com.arinc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {
    private final UserService userService;

    @PreAuthorize("permitAll()")
    @PostMapping
    void createCustomer(@Validated UserRegistrationDto userRegistrationDto) {
        log.info("Creating user: {}", userRegistrationDto);
        userService.saveUser(userRegistrationDto);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/current")
    public UserDto getAuthenticatedUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            log.info("Retrieving user: {}", userDetails.getUsername());
            return userService.findUser(userDetails.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/online")
    public List<UserDto> getOnlineUsers() {
        log.info("Retrieving online users");
        return userService.getOnlineUsers();
    }

}
