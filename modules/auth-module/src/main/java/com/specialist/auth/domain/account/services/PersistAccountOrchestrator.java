package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import jakarta.servlet.http.HttpServletResponse;

public interface PersistAccountOrchestrator {
    ShortAccountResponseDto save(DefaultAccountCreateDto dto, HttpServletResponse response);

    ShortAccountResponseDto save(ManagedAccountCreateDto dto);
}
