package com.aidcompass.auth.core.rate_limit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Profile("proxy-rate-limit")
@Component
public class ProxyRateLimitFilter extends RateLimitFilter {

    public ProxyRateLimitFilter(List<RateLimitRepository> repositories) {
        super(repositories);
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
            repository.increment(ip);
        }
        filterChain.doFilter(request, response);
    }
}
