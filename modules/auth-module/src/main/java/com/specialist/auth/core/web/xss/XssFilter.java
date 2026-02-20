package com.specialist.auth.core.web.xss;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


public class XssFilter extends OncePerRequestFilter {

    private final List<String> METHOD_TO_SANITIZE = List.of("POST", "PUT", "PATCH");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!METHOD_TO_SANITIZE.contains(request.getMethod().toUpperCase())) {
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(new XssHttpRequestWrapper(request), response);
    }
}
