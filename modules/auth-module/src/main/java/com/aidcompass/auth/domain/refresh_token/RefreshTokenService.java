package com.aidcompass.auth.domain.refresh_token;

import com.aidcompass.auth.domain.account.models.AccountUserDetails;
import com.aidcompass.auth.domain.refresh_token.models.RefreshToken;

import java.util.UUID;

public interface RefreshTokenService {
    RefreshToken generateAndSave(AccountUserDetails userDetails);

    boolean isActiveById(UUID id);

    RefreshToken findById(UUID id);

    void deactivateById(UUID id);

    void revokeById(UUID id);
}
