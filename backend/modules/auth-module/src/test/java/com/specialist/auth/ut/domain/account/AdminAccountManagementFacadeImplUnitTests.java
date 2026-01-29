package com.specialist.auth.ut.domain.account;

import com.specialist.auth.core.TokenManager;
import com.specialist.auth.domain.account.models.dtos.DemodeRequest;
import com.specialist.auth.domain.account.models.dtos.DisableRequest;
import com.specialist.auth.domain.account.models.dtos.LockRequest;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.account.services.AdminAccountManagementFacadeImpl;
import com.specialist.auth.domain.account.services.AdminAccountManagementService;
import com.specialist.auth.domain.authority.Authority;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AdminAccountManagementFacadeImplUnitTests {

    @Mock
    private AdminAccountManagementService accountManagementService;

    @Mock
    private TokenManager tokenManager;

    @InjectMocks
    private AdminAccountManagementFacadeImpl facade;

    @Test
    @DisplayName("UT: disableById() should disable account and revoke tokens")
    void disableById_shouldDisableAndRevoke() {
        UUID id = UUID.randomUUID();
        DisableRequest request = new DisableRequest(DisableReason.ATTACK_ATTEMPT_DETECTED);

        facade.disableById(id, request);

        verify(accountManagementService).disableById(id, request);
        verify(tokenManager).revokeAll(id);
        verifyNoMoreInteractions(accountManagementService, tokenManager);
    }

    @Test
    @DisplayName("UT: lockById() should lock account and revoke tokens")
    void lockById_shouldLockAndRevoke() {
        UUID id = UUID.randomUUID();
        LockRequest request = new LockRequest(LockReason.ABUSE, LocalDateTime.now());

        facade.lockById(id, request);

        verify(accountManagementService).lockById(id, request);
        verify(tokenManager).revokeAll(id);
        verifyNoMoreInteractions(accountManagementService, tokenManager);
    }

    @Test
    @DisplayName("UT: demoteById() should take away authorities and revoke tokens")
    void demoteById_shouldTakeAwayAuthoritiesAndRevoke() {
        UUID id = UUID.randomUUID();
        DemodeRequest request = new DemodeRequest();
        request.setAccountId(id);
        request.setAuthorities(Set.of(Authority.ACCOUNT_CREATE));

        facade.demoteById(request);

        verify(accountManagementService).takeAwayAuthoritiesById(id, request.getAuthorities());
        verify(tokenManager).revokeAll(id);
        verifyNoMoreInteractions(accountManagementService, tokenManager);
    }
}
