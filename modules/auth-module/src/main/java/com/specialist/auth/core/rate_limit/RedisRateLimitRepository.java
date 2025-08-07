package com.specialist.auth.core.rate_limit;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@RequiredArgsConstructor
public class RedisRateLimitRepository implements RateLimitRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final String TARGET_URL;
    private final String KEY_TEMPLATE;
    private final Long MAX_ATTEMPT_COUNT;
    private final Long OBSERVE_TIME;
    private final Long LOCK_TIME;

    @Override
    public String getTargetUrl() {
        return TARGET_URL;
    }

    @Override
    public RateLimitStatus increment(String ip) {
        String key = KEY_TEMPLATE.formatted(ip);
        String lockedKey = "blocked:" + KEY_TEMPLATE.formatted(ip);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(lockedKey))) {
            return RateLimitStatus.BLOCKED;
        }
        String rawValue = redisTemplate.opsForValue().get(key);
        if (rawValue == null) {
            redisTemplate.opsForValue().set(key, String.valueOf(1), Duration.ofSeconds(OBSERVE_TIME));
            return RateLimitStatus.ALLOWED;
        } else {
            long attemptCount = Long.parseLong(rawValue);
            if (attemptCount < MAX_ATTEMPT_COUNT) {
                redisTemplate.opsForValue().increment(key, 1);
                return RateLimitStatus.ALLOWED;
            } else {
                redisTemplate.delete(key);
                redisTemplate.opsForValue().set(lockedKey, "blocked", Duration.ofSeconds(LOCK_TIME));
                return RateLimitStatus.BLOCKED;
            }
        }
    }
}
