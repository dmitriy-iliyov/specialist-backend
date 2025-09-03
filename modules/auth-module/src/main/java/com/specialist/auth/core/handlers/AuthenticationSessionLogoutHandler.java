package com.specialist.auth.core.handlers;

import com.specialist.auth.core.SessionCookieManager;
import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public final class AuthenticationSessionLogoutHandler implements LogoutHandler {

    private final SessionCookieManager sessionManager;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UUID refreshTokenId = ((AccessTokenUserDetails) authentication.getPrincipal()).getId();
        sessionManager.terminate(refreshTokenId, response);
    }
}
