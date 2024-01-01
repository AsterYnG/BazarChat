package com.arinc.servlets;

import com.arinc.dto.CustomerDto;
import com.arinc.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final CustomerService customerService = CustomerService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/regAndLog/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        customerService.findUser(req.getParameter("login"), req.getParameter("login-password"))
                .ifPresentOrElse(
                    customerDto -> successLogin(customerDto, req, resp),
                        () -> failLogin(req, resp)
                );
    }

    private void failLogin(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void successLogin(CustomerDto customerDto, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", customerDto);
        try {
            resp.sendRedirect("/home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}