package com.arinc.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class LoginController{

    @GetMapping("/login")
    public String showLoginPage(){
        return "/regAndLog/login";
    }



//    private final CustomerService customerService = CustomerService.getInstance();
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("WEB-INF/regAndLog/login.html").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        customerService.findUser(req.getParameter("login"), req.getParameter("login-password"))
//                .ifPresentOrElse(
//                    customerDto -> successLogin(customerDto, req, resp),
//                        () -> failLogin(req, resp)
//                );
//    }
//
//    private void failLogin(HttpServletRequest req, HttpServletResponse resp) {
//        try {
//            resp.sendRedirect("/login?error&login="+ req.getParameter("login"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void successLogin(CustomerDto customerDto, HttpServletRequest req, HttpServletResponse resp) {
//        req.getSession().setAttribute("user", customerDto);
//        customerService.setOnlineTrue(customerDto);
//        try {
//            resp.sendRedirect("/home");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
