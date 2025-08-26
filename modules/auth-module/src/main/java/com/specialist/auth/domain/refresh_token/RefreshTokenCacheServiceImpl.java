package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenCacheServiceImpl implements RefreshTokenCacheService {

    private final CacheManager cacheManager;

    @Override
    public RefreshToken putToActiveAsTrue(RefreshToken refreshToken) {
        Cache cache = cacheManager.getCache("refresh-tokens:active");
        if (cache != null) {
            cache.put(refreshToken.id(), Boolean.TRUE);
        }
        return refreshToken;
    }

    @Override
    public void putToActiveAsFalse(UUID id) {
        Cache cache = cacheManager.getCache("refresh-tokens:active");
        if (cache != null) {
            cache.put(id, Boolean.FALSE);
        }
    }

    @Override
    public void evictById(UUID id) {
        Cache cache = cacheManager.getCache("refresh-tokens");
        if (cache != null) {
            cache.evict(id);
        }
    }

    @Override
    public RefreshToken put(RefreshToken refreshToken) {
        Cache cache = cacheManager.getCache("refresh-tokens");
        if (cache != null) {
            cache.put(refreshToken.id(), refreshToken);
        }
        return refreshToken;
    }
}
