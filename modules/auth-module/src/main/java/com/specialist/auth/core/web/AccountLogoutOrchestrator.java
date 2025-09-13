package com.specialist.auth.core.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AccountLogoutOrchestrator {
    void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
