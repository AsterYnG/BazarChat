package com.arinc.controllers.rest;

import com.arinc.dto.UserDto;
import com.arinc.service.ImageService;
import com.arinc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping("api/v1/images")
public class ImageRestController {
    private final ImageService imageService;
    private final UserService userService;
    @GetMapping("/user")
    public byte[] getUserImage(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.findUser(userDetails.getUsername())
                .map(UserDto::getUserPic)
                .map(imageService::get)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
