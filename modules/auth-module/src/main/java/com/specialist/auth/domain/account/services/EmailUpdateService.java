package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.dtos.AccountEmailUpdateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;

public interface EmailUpdateService {
    ShortAccountResponseDto update(AccountEmailUpdateDto dto);
}
