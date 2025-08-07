package com.aidcompass.auth.core;

import com.aidcompass.auth.domain.account.models.AccountUserDetails;
import com.aidcompass.auth.domain.service_account.models.ServiceAccountUserDetails;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.UUID;

public interface TokenManager {
    void generate(AccountUserDetails userDetails, HttpServletResponse response);

    Map<String, String> generate(ServiceAccountUserDetails userDetails);

    void refresh(UUID refreshTokenId, HttpServletResponse response);
}
