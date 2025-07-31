package com.aidcompass.auth.domain.account.services;

import com.aidcompass.auth.domain.account.models.AccountCreateDto;
import com.aidcompass.auth.domain.account.models.AccountResponseDto;

import java.util.UUID;

public interface AccountService {
    AccountResponseDto save(AccountCreateDto dto);

    boolean existsByEmail(String email);

    void lockById(UUID id);

    void deleteById(UUID id);

    UUID findIdByEmail(String email);

    void updateEmailById(UUID id, String email);
}
