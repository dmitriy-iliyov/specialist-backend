package com.aidcompass.auth.core.rate_limit;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Profile("proxy-rate-limit")
@Component
public class ProxyRateLimitFilter extends RateLimitFilter {

    private final ObjectMapper mapper;

    public ProxyRateLimitFilter(List<RateLimitRepository> repositories, ObjectMapper mapper) {
        super(repositories);
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (repositoriesMap.containsKey(request.getRequestURI())) {
            RateLimitRepository repository = repositoriesMap.get(request.getRequestURI());
            String xffHeader = request.getHeader("X-Forwarded-For");
            String ip;
            if (xffHeader == null || xffHeader.isBlank()) {
                ip = request.getRemoteAddr();
            } else {
                ip = xffHeader.split(",")[0].trim();
            }
            RateLimitStatus status = repository.increment(ip);
            if (status.equals(RateLimitStatus.BLOCKED)) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write(mapper.writeValueAsString(Map.of("message", "Too many requests, try later.")));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
