package com.specialist.auth.configs;

import com.specialist.auth.core.rate_limit.RedisRateLimitRepository;
import com.specialist.auth.core.rate_limit.ScriptLoader;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.context.annotation.Bean;

//@Configuration
public class RateLimitConfig {

    @Bean("passwordRecoveryRequestRedisRateLimitRepository")
    public RedisRateLimitRepository passwordRecoveryRequestRedisRateLimitRepository(StatefulRedisConnection<String, String> statefulConnection,
                                                                                    ScriptLoader scriptLoader) {
        return new RedisRateLimitRepository(
                statefulConnection,
                scriptLoader,
                "/api/v1/accounts/password-recovery/request",
                "rate-limit:pass-recovery:request",
                2L,
                900L,
                3600L
        );
    }

    @Bean("passwordRecoveryRecoverRedisRateLimitRepository")
    public RedisRateLimitRepository passwordRecoveryRecoverRedisRateLimitRepository(StatefulRedisConnection<String, String> statefulConnection,
                                                                                    ScriptLoader scriptLoader) {
        return new RedisRateLimitRepository(
                statefulConnection,
                scriptLoader,
                "/api/v1/accounts/password-recovery/recover",
                "rate-limit:pass-recovery:recover",
                2L,
                300L,
                1800L
        );
    }

    @Bean("confirmationRequestRedisRateLimitRepository")
    public RedisRateLimitRepository confirmationRequestRedisRateLimitRepository(StatefulRedisConnection<String, String> statefulConnection,
                                                                                ScriptLoader scriptLoader) {
        return new RedisRateLimitRepository(
                statefulConnection,
                scriptLoader,
                "/api/v1/accounts/confirmation/request",
                "rate-limit:confirmation:request",
                3L,
                600L,
                3600L
        );
    }

    @Bean("confirmRedisRateLimitRepository")
    public RedisRateLimitRepository confirmRedisRateLimitRepository(StatefulRedisConnection<String, String> statefulConnection,
                                                                    ScriptLoader scriptLoader) {
        return new RedisRateLimitRepository(
                statefulConnection,
                scriptLoader,
                "/api/v1/accounts/confirmation/confirm",
                "rate-limit:confirmation:confirm",
                3L,
                300L,
                300L
        );
    }

    @Bean("loginRedisRateLimitRepository")
    public RedisRateLimitRepository loginRedisRateLimitRepository(StatefulRedisConnection<String, String> statefulConnection,
                                                                  ScriptLoader scriptLoader) {
        return new RedisRateLimitRepository(
                statefulConnection,
                scriptLoader,
                "/api/auth/login",
                "rate-limit:login",
                5L,
                60L,
                600L
        );
    }

    @Bean("oAuth2AuthorizeRedisRateLimitRepository")
    public RedisRateLimitRepository oAuth2AuthorizeRedisRateLimitRepository(StatefulRedisConnection<String, String> statefulConnection,
                                                                            ScriptLoader scriptLoader) {
        return new RedisRateLimitRepository(
                statefulConnection,
                scriptLoader,
                "/api/auth/oauth2/authorize",
                "rate-limit:oauth2-authorize",
                5L,
                60L,
                600L
        );
    }

    @Bean("accountRegistrationRedisRateLimitRepository")
    public RedisRateLimitRepository accountRegistrationRedisRateLimitRepository(StatefulRedisConnection<String, String> statefulConnection,
                                                                                ScriptLoader scriptLoader) {
        return new RedisRateLimitRepository(
                statefulConnection,
                scriptLoader,
                "/api/v1/accounts",
                "rate-limit:accounts:registration",
                5L,
                60L,
                600L
        );
    }
}