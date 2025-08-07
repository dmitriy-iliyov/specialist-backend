package com.specialist.auth.core;

import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.UUID;

public interface TokenManager {
    void generate(AccountUserDetails userDetails, HttpServletResponse response);

    Map<String, String> generate(ServiceAccountUserDetails userDetails);

    void refresh(UUID refreshTokenId, HttpServletResponse response);
}
