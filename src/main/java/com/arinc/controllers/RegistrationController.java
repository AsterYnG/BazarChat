package com.arinc.controllers;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("permitAll()")

public class RegistrationController extends HttpServlet {


    @GetMapping("/registration")
    String showRegistrationPage(){
        return "regAndLog/registration";
    }
//    private final CustomerService customerService = CustomerService.getInstance();
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("WEB-INF/regAndLog/registration.html").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        var registrationDto = CustomerRegistrationDto.builder()
//                .login(req.getParameter("register-username"))
//                .password(req.getParameter("register-password"))
//                .userPic(req.getPart("user-pic"))
//                .build();
//        customerService.saveUser(registrationDto);
//    }
}
