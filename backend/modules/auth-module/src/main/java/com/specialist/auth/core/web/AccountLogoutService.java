package com.specialist.auth.core.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountLogoutService {
    void logout(HttpServletRequest request, HttpServletResponse response);
}
