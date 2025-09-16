package com.specialist.contracts.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Set;
import java.util.UUID;

public record DemoteRequest(
        UUID accountId,
        Set<String> authorities,
        HttpServletRequest request,
        HttpServletResponse response
) { }
