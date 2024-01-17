package com.arinc.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet("/theme")
public class ThemeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkTheme(req, resp);
        resp.sendRedirect("/home");
    }
    private void checkTheme(HttpServletRequest req, HttpServletResponse resp){
        if (req.getSession().isNew()){
            req.getSession().setAttribute("theme","dark");
        }
        if (req.getParameter("theme") != null) {
            if(req.getSession().getAttribute("theme").toString().equals("dark")){
                req.getSession().setAttribute("theme","light");
            }
            else  req.getSession().setAttribute("theme","dark");
        }
    }
}
