package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import org.springframework.cache.annotation.CachePut;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface RefreshTokenService {
    RefreshToken generateAndSave(AccountUserDetails userDetails);

    @CachePut(value = "refresh-tokens", key = "#result.id()")
    @Transactional
    RefreshToken generateAndSave(ServiceAccountUserDetails userDetails);

    boolean isActiveById(UUID id);

    RefreshToken findById(UUID id);

    void deactivateById(UUID id);

    void revokeById(UUID id);
}
