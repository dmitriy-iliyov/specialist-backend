package com.specialist.auth.core.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CookieAccountLogoutService implements AccountLogoutService {

    private final LogoutHandler sessionCookieLogoutHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;

    public CookieAccountLogoutService(@Qualifier("sessionCookieLogoutHandler") LogoutHandler sessionCookieLogoutHandler,
                                      LogoutSuccessHandler logoutSuccessHandler) {
        this.sessionCookieLogoutHandler = sessionCookieLogoutHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        sessionCookieLogoutHandler.logout(request, response, authentication);
        logoutSuccessHandler.onLogoutSuccess(request, response, authentication);
    }
}
