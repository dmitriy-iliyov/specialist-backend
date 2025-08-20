package com.specialist.auth.core.rate_limit;

public interface RateLimitRepository {
    String getTargetUrl();
    RateLimitStatus increment(String remoteAddr);
}
