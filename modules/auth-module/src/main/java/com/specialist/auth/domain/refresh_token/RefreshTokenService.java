package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.core.BaseUserDetails;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;

import java.util.UUID;

public interface RefreshTokenService {
    RefreshToken generateAndSave(BaseUserDetails userDetails);

    boolean isActiveById(UUID id);

    RefreshToken findById(UUID id);

    void deleteById(UUID id);

    void deleteAllByAccountId(UUID accountId);
}
