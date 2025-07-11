package com.aidcompass.core.security.auth.services;

import com.aidcompass.core.security.auth.dto.AuthRequest;
import com.aidcompass.core.security.domain.authority.models.Authority;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface UserAuthService {
    void login(AuthRequest requestDto, HttpServletRequest request, HttpServletResponse response);

    void changeAuthorityById(UUID id, Authority authority, HttpServletResponse response);
}
