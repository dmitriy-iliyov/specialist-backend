package com.aidcompass.auth.domain.account.services;

import com.aidcompass.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.aidcompass.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.aidcompass.auth.domain.account.models.dtos.ShortAccountResponseDto;

public interface AccountOrchestrator {
    ShortAccountResponseDto save(DefaultAccountCreateDto dto);

    ShortAccountResponseDto save(ManagedAccountCreateDto dto);
}
