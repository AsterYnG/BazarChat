package com.arinc.servlets;

import com.arinc.dto.CustomerDto;
import com.arinc.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private final CustomerService customerService = CustomerService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (CustomerDto) req.getSession().getAttribute("user");
        customerService.setOnlineFalse(user);
        req.getSession().invalidate();
        resp.sendRedirect("/login");
    }
}
