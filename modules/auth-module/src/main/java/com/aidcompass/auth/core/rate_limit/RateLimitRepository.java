package com.aidcompass.auth.core.rate_limit;

public interface RateLimitRepository {
    String getTargetUrl();

    RateLimitStatus increment(String remoteAddr);
}
