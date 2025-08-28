package com.specialist.user.services;

import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface DeleteUserOrchestrator {
    void delete(UUID id, UUID refreshTokenId, HttpServletResponse response);
}
