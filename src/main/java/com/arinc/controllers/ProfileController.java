package com.arinc.controllers;

import com.arinc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String getProfilePage() {
        return "profile/profile";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{login}")
    public String getForeignProfile(@PathVariable String login){
        return userService.findUser(login).map(user -> "profile/foreign-profile")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public String getProfileEditPage() {
        return "profile/edit";
    }
}
