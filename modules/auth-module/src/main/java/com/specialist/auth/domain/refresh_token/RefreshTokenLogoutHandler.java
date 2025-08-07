package com.specialist.auth.domain.refresh_token;

import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenLogoutHandler implements LogoutHandler {

    private final RefreshTokenService service;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        AccessTokenUserDetails userDetails = (AccessTokenUserDetails) authentication.getPrincipal();
        service.deactivateById(userDetails.getId());
    }
}
