package com.specialist.contracts.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public record UserCompleteEvent(
        UUID userId,
        String userType,
        HttpServletRequest request,
        HttpServletResponse response
) { }
