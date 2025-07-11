package com.aidcompass.core.security.handlers;

import com.aidcompass.core.security.domain.token.TokenUserDetailsService;
import com.aidcompass.core.security.handlers.logout.DeactivatingTokenLogoutHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDeleteHandler {

    private final TokenUserDetailsService tokenUserDetailsService;


    public void handle(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        CookieClearingLogoutHandler handler = new CookieClearingLogoutHandler(
                "__Host-auth_token", "auth_info", "XSRF-TOKEN"
        );
        handler.logout(request, response, authentication);
        DeactivatingTokenLogoutHandler deactivatingTokenLogoutHandler = new DeactivatingTokenLogoutHandler(tokenUserDetailsService);
        deactivatingTokenLogoutHandler.logout(request, response, authentication);
        SecurityContextHolder.clearContext();
    }
}
