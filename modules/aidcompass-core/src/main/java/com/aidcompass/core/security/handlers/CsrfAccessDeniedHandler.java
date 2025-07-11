package com.aidcompass.core.security.handlers;

import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CsrfAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    private final MessageSource messageSource;
    private final ObjectMapper mapper;


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Locale locale = request.getLocale();
        ProblemDetail problemDetail;

        if (accessDeniedException instanceof CsrfException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,
                    messageSource.getMessage("403", null, "csrf.error.403", locale));
            problemDetail.setTitle("CSRF protection");
            problemDetail.setProperty("properties", Map.of("errors",
                    List.of(new ErrorDto("csrf_token", "Invalid or missing CSRF token!")))
            );
        } else {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,
                    messageSource.getMessage("403", null, "access_denied.error.400", locale));
            problemDetail.setTitle("Access Denied");
            problemDetail.setProperty("properties", Map.of("errors",  List.of(new ErrorDto("field", "Access denied!"))));
        }

        response.setStatus(problemDetail.getStatus());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        mapper.writeValue(response.getWriter(), problemDetail);
    }
}
