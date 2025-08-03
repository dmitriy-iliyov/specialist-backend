package com.aidcompass.auth.core.rate_limit;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PasswordRecoveryRateLimitRepository implements RateLimitRepository {

    private final String TARGET_URL = "/api/v1/accounts/password-recovery";
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String getTargetUrl() {
        return "";
    }

    @Override
    public void increment(String remoteAddr) {

    }
}
