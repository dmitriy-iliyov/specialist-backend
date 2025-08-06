package com.aidcompass.auth.core;

import com.aidcompass.auth.domain.account.models.AccountUserDetails;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface TokenManager {
    void generate(AccountUserDetails userDetails, HttpServletResponse response);

    void refresh(UUID refreshTokenId, HttpServletResponse response);
}
