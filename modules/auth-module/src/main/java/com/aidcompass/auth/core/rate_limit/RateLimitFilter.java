package com.aidcompass.auth.core.rate_limit;

import org.springframework.web.filter.OncePerRequestFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RateLimitFilter extends OncePerRequestFilter {

    protected final Map<String, RateLimitRepository> repositoriesMap;

    public RateLimitFilter(List<RateLimitRepository> repositories) {
        repositoriesMap = new HashMap<>();
        repositories.forEach(repository -> repositoriesMap.put(repository.getTargetUrl(), repository));
    }
}
