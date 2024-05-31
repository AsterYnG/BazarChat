package com.arinc.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String getProfilePage() {
        return "profile/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public String getProfileEditPage() {
        return "profile/edit";
    }
}
