package com.specialist.auth.domain.access_token;


import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AccessTokenFactoryImpl implements AccessTokenFactory {

    @Value("${api.access-token.ttl}")
    public Long TOKEN_TTL;

    @Override
    public AccessToken generate(RefreshToken refreshToken) {
        Instant createdAt = Instant.now();
        Instant expiresAt = createdAt.plusSeconds(TOKEN_TTL);
        return new AccessToken(
                refreshToken.id(),
                refreshToken.accountId(),
                refreshToken.authorities(),
                createdAt,
                expiresAt
        );
    }
}
