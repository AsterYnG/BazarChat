package com.arinc.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/*")
public class ThemeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var req = (HttpServletRequest)servletRequest;
        if (req.getSession().isNew()){
            req.getSession().setAttribute("theme","light");
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
