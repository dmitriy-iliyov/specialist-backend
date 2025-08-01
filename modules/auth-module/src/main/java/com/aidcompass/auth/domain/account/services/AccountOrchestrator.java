package com.aidcompass.auth.domain.account.services;

import com.aidcompass.auth.domain.account.models.dtos.AccountCreateDto;
import com.aidcompass.auth.domain.account.models.dtos.ShortAccountResponseDto;

public interface AccountOrchestrator {
    ShortAccountResponseDto save(AccountCreateDto dto);
}
