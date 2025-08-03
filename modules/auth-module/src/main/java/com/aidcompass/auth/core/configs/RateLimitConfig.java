package com.aidcompass.auth.core.configs;

import com.aidcompass.auth.core.rate_limit.RedisRateLimitRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RateLimitConfig {

    @Bean("passwordRecoveryRequestRedisRateLimitRepository")
    public RedisRateLimitRepository passwordRecoveryRequestRedisRateLimitRepository(RedisTemplate<String, String> redisTemplate) {
        return new RedisRateLimitRepository(
                redisTemplate,
                "/api/v1/accounts/password-recovery/request",
                "rate-limit:pass-recovery:request:",
                2L,
                900L,
                3600L
        );
    }

    @Bean("passwordRecoveryRecoverRedisRateLimitRepository")
    public RedisRateLimitRepository passwordRecoveryRecoverRedisRateLimitRepository(RedisTemplate<String, String> redisTemplate) {
        return new RedisRateLimitRepository(
                redisTemplate,
                "/api/v1/accounts/password-recovery/recover",
                "rate-limit:pass-recovery:recover:",
                3L,
                300L,
                1800L
        );
    }

    @Bean("confirmationRequestRedisRateLimitRepository")
    public RedisRateLimitRepository confirmationRequestRedisRateLimitRepository(RedisTemplate<String, String> redisTemplate) {
        return new RedisRateLimitRepository(
                redisTemplate,
                "/api/v1/accounts/confirmation/request",
                "rate-limit:confirmation:request:",
                3L,
                600L,
                3600L
        );
    }

    @Bean("confirmRedisRateLimitRepository")
    public RedisRateLimitRepository confirmRedisRateLimitRepository(RedisTemplate<String, String> redisTemplate) {
        return new RedisRateLimitRepository(
                redisTemplate,
                "/api/v1/accounts/confirmation/confirm",
                "rate-limit:confirmation:confirm:",
                2L,
                300L,
                120L
        );
    }

    @Bean("loginRedisRateLimitRepository")
    public RedisRateLimitRepository loginRedisRateLimitRepository(RedisTemplate<String, String> redisTemplate) {
        return new RedisRateLimitRepository(
                redisTemplate,
                "/api/v1/auth/login",
                "rate-limit:login:",
                5L,
                60L,
                600L
        );
    }
}