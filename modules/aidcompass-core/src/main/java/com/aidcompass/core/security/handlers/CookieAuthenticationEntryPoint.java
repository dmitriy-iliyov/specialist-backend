package com.aidcompass.core.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class CookieAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    private final ObjectMapper mapper;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("Authentication Required");
        problemDetail.setDetail("Your session may have expired or you are not logged in!");
        problemDetail.setProperty("errorCode", "auth.401");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        response.setStatus(problemDetail.getStatus());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        mapper.writeValue(response.getWriter(), problemDetail);
    }
}
