package com.specialist.contracts.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public record UserCompleteEvent(
        UUID userId,
        UserType userType,
        HttpServletRequest request,
        HttpServletResponse response
) { }
