package com.arinc.controllers.rest;

import com.arinc.dto.UserDto;
import com.arinc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class ProfileRestController {
    private final UserService userService;

    @GetMapping("profile/")
    public ResponseEntity<UserDto> getProfile(@AuthenticationPrincipal UserDetails userDetails){
        Optional<UserDto> currentUser = userService.findUser(userDetails.getUsername());
        return currentUser.map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }
}
