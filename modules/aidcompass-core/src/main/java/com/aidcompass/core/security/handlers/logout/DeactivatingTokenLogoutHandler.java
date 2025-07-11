package com.aidcompass.core.security.handlers.logout;

import com.aidcompass.core.security.domain.token.TokenUserDetailsService;
import com.aidcompass.core.security.domain.token.models.TokenEntity;
import com.aidcompass.core.security.domain.token.models.TokenUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.time.Instant;

@RequiredArgsConstructor
public class DeactivatingTokenLogoutHandler implements LogoutHandler {

    private final TokenUserDetailsService tokenUserDetailsService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if(authentication != null && authentication.getPrincipal() instanceof TokenUserDetails tokenUserDetails) {
            tokenUserDetailsService.save(new TokenEntity(tokenUserDetails.getToken().getId(), Instant.now()));
        }
    }
}
