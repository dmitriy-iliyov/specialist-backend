package com.specialist.auth.infrastructure.message.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ConfirmationFacade {
    void confirm(String code, HttpServletRequest request, HttpServletResponse response);
}
