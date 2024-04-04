package com.arinc.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
//    private final CustomerService customerService = CustomerService.getInstance();
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        var user = (CustomerDto) req.getSession().getAttribute("user");
//        customerService.setOnlineFalse(user);
//        req.getSession().invalidate();
//        resp.sendRedirect("/login");
//    }
}
