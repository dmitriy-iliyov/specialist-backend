package com.specialist.auth.core;

import com.specialist.auth.core.models.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountLoginOrchestrator {
    void login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response);
}
