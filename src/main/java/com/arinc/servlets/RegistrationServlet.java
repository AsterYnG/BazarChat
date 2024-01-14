package com.arinc.servlets;

import com.arinc.dto.CustomerDto;
import com.arinc.dto.CustomerRegistrationDto;
import com.arinc.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final CustomerService customerService = CustomerService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/regAndLog/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var registrationDto = CustomerRegistrationDto.builder()
                .login(req.getParameter("register-username"))
                .password(req.getParameter("register-password"))
                .userPic(req.getPart("user-pic"))
                .build();
        customerService.saveUser(registrationDto);
    }
}
