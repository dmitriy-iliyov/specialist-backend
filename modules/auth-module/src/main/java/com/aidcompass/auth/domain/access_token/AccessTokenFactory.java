package com.aidcompass.auth.domain.access_token;

import com.aidcompass.auth.domain.access_token.models.AccessToken;
import com.aidcompass.auth.domain.refresh_token.models.RefreshToken;

public interface AccessTokenFactory {
    AccessToken generate(RefreshToken refreshToken);
}
