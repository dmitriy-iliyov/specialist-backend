package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.oauth2.provider.Provider;
import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountPersistOrchestrator {
    ShortAccountResponseDto save(DefaultAccountCreateDto dto, HttpServletResponse response);

    ShortAccountResponseDto save(String email, Provider provider);

    ShortAccountResponseDto save(ManagedAccountCreateDto dto);
}
