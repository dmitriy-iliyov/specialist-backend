package com.aidcompass.auth.domain.account.services;

import com.aidcompass.auth.domain.account.models.AccountFilter;
import com.aidcompass.auth.domain.account.models.dtos.*;
import com.aidcompass.utils.pagination.PageResponse;

import java.util.UUID;

public interface AccountService {
    ShortAccountResponseDto save(DefaultAccountCreateDto dto);

    boolean existsByEmail(String email);

    void lockById(UUID id, LockRequest request);

    void deleteById(UUID id);

    UUID findIdByEmail(String email);

    void confirmEmail(String email);

    ShortAccountResponseDto update(AccountUpdateDto dto);

    void updateEmailById(UUID id, String email);

    void updatePasswordByEmail(String email, String password);

    PageResponse<AccountResponseDto> findAll(com.aidcompass.utils.pagination.PageRequest page);

    PageResponse<AccountResponseDto> findAllByFilter(AccountFilter filter);

    void setUnableById(UUID id, UnableRequest request);
}
