package com.aidcompass.auth.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface AuthService {
    void postConfirmationLogin(String email, HttpServletResponse response);

    void login(LoginRequest requestDto, HttpServletRequest request, HttpServletResponse response);

    void refresh(UUID refreshTokenId, HttpServletResponse response);
}
