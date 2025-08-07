package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;

public interface AccessTokenFactory {
    AccessToken generate(RefreshToken refreshToken);
}
