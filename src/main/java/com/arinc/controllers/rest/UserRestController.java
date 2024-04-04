package com.arinc.controllers.rest;

import com.arinc.dto.UserDto;
import com.arinc.dto.UserRegistrationDto;
import com.arinc.service.UserService;
import lombok.RequiredArgsConstructor;
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

import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createCustomer(@Validated UserRegistrationDto userRegistrationDto){
        userService.saveUser(userRegistrationDto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/current")
    public UserDto getAuthenticatedUser(@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null){
        return userService.findUser(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
