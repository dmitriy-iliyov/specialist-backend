package com.specialist.contracts.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public record ProfileCreateEvent(
        UUID accountId,
        ProfileType profileType,
        HttpServletRequest request,
        HttpServletResponse response
) { }
