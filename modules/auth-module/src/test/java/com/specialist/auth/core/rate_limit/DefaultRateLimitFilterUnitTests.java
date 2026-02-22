package com.specialist.auth.core.rate_limit;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultRateLimitFilterUnitTests {

    @Mock
    private RateLimitRepository repository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private DefaultRateLimitFilter filter;

    @BeforeEach
    void setUp() {
        when(repository.getTargetUrl()).thenReturn("/api/limited");
        filter = new DefaultRateLimitFilter(List.of(repository), mapper);
    }

    @Test
    @DisplayName("UT: doFilterInternal() when URI not limited should proceed")
    void doFilterInternal_whenUriNotLimited_shouldProceed() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/unlimited");

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(repository, never()).increment(any());
        verifyNoMoreInteractions(filterChain, repository);
    }

    @Test
    @DisplayName("UT: doFilterInternal() when URI limited and status OK should proceed")
    void doFilterInternal_whenUriLimitedAndOk_shouldProceed() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/limited");
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(repository.increment("127.0.0.1")).thenReturn(RateLimitStatus.ALLOWED);

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(repository).increment("127.0.0.1");
        verifyNoMoreInteractions(filterChain, repository);
    }

    @Test
    @DisplayName("UT: doFilterInternal() when URI limited and status BLOCKED should return 429")
    void doFilterInternal_whenUriLimitedAndBlocked_shouldReturn429() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/limited");
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(repository.increment("127.0.0.1")).thenReturn(RateLimitStatus.BLOCKED);
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
        when(mapper.writeValueAsString(any())).thenReturn("json");

        filter.doFilter(request, response, filterChain);

        verify(filterChain, never()).doFilter(request, response);
        verify(response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        verify(response.getWriter()).write("json");
        verifyNoMoreInteractions(filterChain, repository);
    }
}
