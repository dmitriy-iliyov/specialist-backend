package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.AccountFilter;
import com.specialist.auth.domain.account.models.dtos.*;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;
import com.specialist.contracts.auth.DeferAccountDeleteEvent;
import com.specialist.utils.pagination.PageResponse;

import java.time.Instant;
import java.util.List;
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

    List<DeferAccountDeleteEvent> findAllByDisableReasonAndThreshold(DisableReason disableReason, Instant threshold, int batchSize);

    ShortAccountResponseDto updatePassword(AccountPasswordUpdateDto dto);

    ShortAccountResponseDto updateEmail(AccountEmailUpdateDto dto);

    void softDeleteById(UUID id);

    void deleteById(UUID id);

    void deleteAllByIdIn(Set<UUID> ids);
}
