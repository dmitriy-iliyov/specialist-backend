package com.aidcompass.auth.domain.account.services;

import com.aidcompass.auth.domain.account.models.AccountFilter;
import com.aidcompass.auth.domain.account.models.dtos.AccountCreateDto;
import com.aidcompass.auth.domain.account.models.dtos.AccountResponseDto;
import com.aidcompass.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.aidcompass.auth.domain.account.models.dtos.LockDto;
import com.aidcompass.utils.pagination.PageResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface AccountService {
    ShortAccountResponseDto save(AccountCreateDto dto);

    boolean existsByEmail(String email);

    void lockById(UUID id, LockDto dto);

    void deleteById(UUID id);

    UUID findIdByEmail(String email);

    void confirmEmailById(UUID id);

    void updateEmailById(UUID id, String email);

    @Transactional(readOnly = true)
    PageResponse<AccountResponseDto> findAll(com.aidcompass.utils.pagination.PageRequest page);

    PageResponse<AccountResponseDto> findAllByFilter(AccountFilter filter);
}
