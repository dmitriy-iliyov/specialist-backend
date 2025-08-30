package com.specialist.auth.core.configs;

import com.specialist.auth.core.rate_limit.RedisRateLimitRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;

//@Configuration
public class RateLimitConfig {

    @Bean("passwordRecoveryRequestRedisRateLimitRepository")
    public RedisRateLimitRepository passwordRecoveryRequestRedisRateLimitRepository(RedisTemplate<String, String> redisTemplate) {
        return new RedisRateLimitRepository(
                redisTemplate,
                "/api/v1/accounts/password-recovery/request",
                "rate-limit:pass-recovery:request",
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
                "rate-limit:pass-recovery:recover",
                2L,
                300L,
                1800L
        );
    }

    @Bean("confirmationRequestRedisRateLimitRepository")
    public RedisRateLimitRepository confirmationRequestRedisRateLimitRepository(RedisTemplate<String, String> redisTemplate) {
        return new RedisRateLimitRepository(
                redisTemplate,
                "/api/v1/accounts/confirmation/request",
                "rate-limit:confirmation:request",
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
                "rate-limit:confirmation:confirm",
                3L,
                300L,
                300L
        );
    }

    @Bean("loginRedisRateLimitRepository")
    public RedisRateLimitRepository loginRedisRateLimitRepository(RedisTemplate<String, String> redisTemplate) {
        return new RedisRateLimitRepository(
                redisTemplate,
                "/api/auth/login",
                "rate-limit:login",
                5L,
                60L,
                600L
        );
    }

    @Bean("oAuth2LoginRedisRateLimitRepository")
    public RedisRateLimitRepository oAuth2LoginRedisRateLimitRepository(RedisTemplate<String, String> redisTemplate) {
        return new RedisRateLimitRepository(
                redisTemplate,
                "/api/auth/oauth2/login",
                "rate-limit:oauth2-login",
                5L,
                60L,
                600L
        );
    }

    @Bean("accountRegistrationRedisRateLimitRepository")
    public RedisRateLimitRepository accountRegistrationRedisRateLimitRepository(RedisTemplate<String, String> redisTemplate) {
        return new RedisRateLimitRepository(
                redisTemplate,
                "/api/v1/accounts",
                "rate-limit:accounts:registration",
                5L,
                60L,
                600L
        );
    }
}