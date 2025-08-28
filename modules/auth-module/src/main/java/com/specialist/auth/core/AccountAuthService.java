package com.specialist.auth.core;

import com.specialist.auth.core.models.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface AccountAuthService {
    void postEmailConfirmationLogin(String email, HttpServletRequest request, HttpServletResponse response);

    void login(LoginRequest requestDto, HttpServletRequest request, HttpServletResponse response);

    void refresh(UUID refreshTokenId, HttpServletResponse response);

    void logout(UUID refreshTokenId, HttpServletResponse response);

    void logoutFromAll(UUID accountId, HttpServletResponse response);
}
