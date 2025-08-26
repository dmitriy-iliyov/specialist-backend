package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.domain.refresh_token.models.RefreshToken;

import java.util.UUID;

public interface RefreshTokenCacheService {

    RefreshToken putToActiveAsTrue(RefreshToken refreshToken);

    void putToActiveAsFalse(UUID id);

    void evictById(UUID id);

    RefreshToken put(RefreshToken refreshToken);
}
