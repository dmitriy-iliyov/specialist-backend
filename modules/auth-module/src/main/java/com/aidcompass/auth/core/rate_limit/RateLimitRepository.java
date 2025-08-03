package com.aidcompass.auth.core.rate_limit;

public interface RateLimitRepository {
    String getTargetUrl();

    void increment(String remoteAddr);
}
