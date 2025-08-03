package com.aidcompass.auth.core.rate_limit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Profile("default-rate-limit")
@Component
public class DefaultRateLimitFilter extends RateLimitFilter {

    public DefaultRateLimitFilter(List<RateLimitRepository> repositories) {
        super(repositories);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (repositoriesMap.containsKey(request.getRequestURI())) {
            RateLimitRepository repository = repositoriesMap.get(request.getRequestURI());
            repository.increment(request.getRemoteAddr());
        }
        filterChain.doFilter(request, response);
    }
}
