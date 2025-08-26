package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.oauth2.provider.Provider;
import com.specialist.auth.domain.account.models.AccountFilter;
import com.specialist.auth.domain.account.models.dtos.*;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface AccountService {
    ShortAccountResponseDto save(DefaultAccountCreateDto dto);

    ShortAccountResponseDto save(OAuth2AccountCreateDto dto);

    boolean existsByEmail(String email);

    void lockById(UUID id, LockRequest request);

    void deleteById(UUID id);

    UUID findIdByEmail(String email);

    void confirmEmail(String email);

    ShortAccountResponseDto update(AccountUpdateDto dto);

    void updateEmailById(UUID id, String email);

    void updatePasswordByEmail(String email, String password);

    PageResponse<AccountResponseDto> findAll(com.specialist.utils.pagination.PageRequest page);

    PageResponse<AccountResponseDto> findAllByFilter(AccountFilter filter);

    void unlockById(UUID id);

    void disableById(UUID id, DisableRequest request);

    void enableById(UUID id);

    Provider findProviderByEmail(String email);
}
