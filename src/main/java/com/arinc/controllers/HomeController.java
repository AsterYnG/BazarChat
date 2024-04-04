package com.arinc.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.jasper.security.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/home")
    public String showHomePage(){
        return "home/home";
    }

}
