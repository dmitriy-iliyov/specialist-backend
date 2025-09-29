package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.AccountFilter;
import com.specialist.auth.domain.account.models.dtos.*;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;
import com.specialist.utils.pagination.PageResponse;

import java.util.Set;
import java.util.UUID;

public interface AccountService {
    ShortAccountResponseDto save(DefaultAccountCreateDto dto);

    ShortAccountResponseDto save(OAuth2AccountCreateDto dto);

    boolean existsByEmail(String email);

    ShortAccountResponseDto updateRoleAndAuthoritiesById(UUID id, Role role, Set<Authority> authorities);

    Role findRoleById(UUID id);

    Provider findProviderByEmail(String email);

    UUID findIdByEmail(String email);

    PageResponse<AccountResponseDto> findAllByFilter(AccountFilter filter);

    ShortAccountResponseDto updatePassword(AccountPasswordUpdateDto dto);

    ShortAccountResponseDto updateEmail(AccountEmailUpdateDto dto);

    void deleteById(UUID id);
}
