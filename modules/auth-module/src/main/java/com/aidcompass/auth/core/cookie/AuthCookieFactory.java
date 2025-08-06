package com.aidcompass.auth.core.cookie;

import com.aidcompass.auth.domain.access_token.models.AccessToken;
import com.aidcompass.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.aidcompass.auth.domain.refresh_token.models.RefreshToken;
import jakarta.servlet.http.Cookie;

public interface AuthCookieFactory {

    Cookie generate(RefreshToken refreshToken);

    Cookie generate(AccessToken accessToken);

    Cookie generateEmpty(TokenType type);
}
