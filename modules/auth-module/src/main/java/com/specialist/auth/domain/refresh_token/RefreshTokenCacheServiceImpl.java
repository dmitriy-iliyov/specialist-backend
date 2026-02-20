package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenCacheServiceImpl implements RefreshTokenCacheService {

    private static final String CACHE_KEY_TEMPLATE = "refresh-tokens:active::%s";
    private static final Duration DEFAULT_TTL = Duration.ofDays(1);
    private final RedisTemplate<String, Boolean> redisTemplate;

    @Override
    public RefreshToken putToActiveAsTrue(RefreshToken refreshToken) {
        long ttl = Duration.between(Instant.now(), refreshToken.expiresAt()).toSeconds();
        if (ttl < 0) {
            return refreshToken;
        }
        redisTemplate.opsForValue().set(
                CACHE_KEY_TEMPLATE.formatted(refreshToken.id()),
                Boolean.TRUE,
                ttl,
                TimeUnit.SECONDS
        );
        return refreshToken;
    }

    @Override
    public Boolean isActiveById(UUID id) {
        return redisTemplate.opsForValue().get(CACHE_KEY_TEMPLATE.formatted(id));
    }

    @Override
    public void putToActiveAsFalse(UUID id) {
        redisTemplate.opsForValue().set(
                CACHE_KEY_TEMPLATE.formatted(id),
                false,
                DEFAULT_TTL.toSeconds(),
                TimeUnit.SECONDS
        );
    }

    @Override
    public void evictAllByIdIn(Set<UUID> ids) {
        redisTemplate.delete(
                ids.stream()
                        .map(CACHE_KEY_TEMPLATE::formatted)
                        .toList()
        );
    }
}
