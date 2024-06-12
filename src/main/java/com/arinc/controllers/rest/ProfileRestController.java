package com.arinc.controllers.rest;

import com.arinc.dto.UserDto;
import com.arinc.dto.UserUpdateProfileDto;
import com.arinc.mapper.UserMapper;
import com.arinc.service.ImageService;
import com.arinc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/profile/")
@RequiredArgsConstructor
@Slf4j
public class ProfileRestController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<UserDto> currentUser = userService.findUser(userDetails.getUsername());
        log.info("Getting user: {} By User: {}", currentUser, userDetails.getUsername());
        return currentUser.map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserDto> getProfileByLogin(@PathVariable String login) {
        return userService.findUser(login).map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/edit")
    public ResponseEntity<UserDto> editFullName(@AuthenticationPrincipal UserDetails userDetails, UserUpdateProfileDto userUpdateDto) {
        log.info("Updating user entity: {} By User: {}", userUpdateDto, userDetails.getUsername());
        return ResponseEntity.ok(userService.updateUser(userDetails.getUsername(), userUpdateDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")));
    }
}
