package com.specialist.auth.core;

import com.specialist.auth.core.models.Token;
import com.specialist.auth.core.models.TokenType;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;

import java.util.Map;
import java.util.UUID;

public interface TokenManager {
    Map<TokenType, Token> generate(AccountUserDetails userDetails);

    Token generate(ServiceAccountUserDetails userDetails);

    Token refresh(UUID refreshTokenId);
}
