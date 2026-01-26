package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.domain.refresh_token.models.RefreshToken;

import java.util.Set;
import java.util.UUID;

public interface RefreshTokenCacheService {
    RefreshToken putToActiveAsTrue(RefreshToken refreshToken);

    Boolean isActiveById(UUID id);

    void putToActiveAsFalse(UUID id);

    void evictAllByIdIn(Set<UUID> ids);
}
