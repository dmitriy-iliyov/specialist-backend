package com.specialist.auth.core;

import com.specialist.auth.domain.account.models.AccountUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface SessionCookieManager {
    void create(AccountUserDetails userDetails, HttpServletRequest request, HttpServletResponse response);

    void refresh(UUID refreshTokenId, HttpServletResponse response);

    void terminate(UUID refreshTokenId, HttpServletResponse response);

    void terminateAll(UUID accountId, HttpServletResponse response);
}
