package com.arinc.servlets;

import com.arinc.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private static final CustomerService customerService = CustomerService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("onlineUsers", customerService.getOnlineUsers());
        req.getRequestDispatcher("WEB-INF/home/home-page.jsp").forward(req,resp);
    }
}
