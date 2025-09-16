package com.specialist.auth.core.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountLoginService {
    void login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response);
}
