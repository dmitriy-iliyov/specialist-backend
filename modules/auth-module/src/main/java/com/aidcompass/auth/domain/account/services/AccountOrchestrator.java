package com.aidcompass.auth.domain.account.services;

import com.aidcompass.auth.domain.account.models.AccountCreateDto;
import com.aidcompass.auth.domain.account.models.AccountResponseDto;

public interface AccountOrchestrator {
    AccountResponseDto save(AccountCreateDto dto);
}
