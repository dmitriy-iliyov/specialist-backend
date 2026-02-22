package com.specialist.auth.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.csrf.CsrfException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAccessDeniedHandlerUnitTests {

    @Mock
    private MessageSource messageSource;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private DefaultAccessDeniedHandler accessDeniedHandler;

    @Test
    @DisplayName("UT: handle() with CsrfException should write CSRF error to response")
    void handle_csrfException_shouldWriteCsrfError() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        CsrfException csrfException = new CsrfException("Invalid CSRF token");
        PrintWriter writer = mock(PrintWriter.class);

        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(response.getWriter()).thenReturn(writer);
        when(messageSource.getMessage(eq("403"), any(), any(), any())).thenReturn("CSRF error message");

        accessDeniedHandler.handle(request, response, csrfException);

        verify(response).setStatus(HttpStatus.FORBIDDEN.value());
        verify(response).setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        verify(mapper).writeValue(eq(writer), any());
    }

    @Test
    @DisplayName("UT: handle() with general AccessDeniedException should write access denied error to response")
    void handle_generalAccessDeniedException_shouldWriteAccessDeniedError() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AccessDeniedException accessDeniedException = new AccessDeniedException("Access is denied");
        PrintWriter writer = mock(PrintWriter.class);

        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(response.getWriter()).thenReturn(writer);
        when(messageSource.getMessage(eq("403"), any(), any(), any())).thenReturn("Access denied message");

        accessDeniedHandler.handle(request, response, accessDeniedException);

        verify(response).setStatus(HttpStatus.FORBIDDEN.value());
        verify(response).setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        verify(mapper).writeValue(eq(writer), any());
    }
}
