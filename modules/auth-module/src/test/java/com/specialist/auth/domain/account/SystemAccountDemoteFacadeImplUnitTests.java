package com.specialist.auth.domain.account;

import com.specialist.auth.core.web.AccountLoginService;
import com.specialist.auth.core.web.LoginRequest;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.services.SystemAccountDemoteFacadeImpl;
import com.specialist.auth.domain.account.services.SystemAccountManagementService;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.contracts.auth.DemoteRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SystemAccountDemoteFacadeImplUnitTests {

    @Mock
    private SystemAccountManagementService managementService;

    @Mock
    private AccountLoginService loginService;

    @InjectMocks
    private SystemAccountDemoteFacadeImpl facade;

    @Test
    @DisplayName("UT: demote() should take away authorities and login")
    void demote_shouldTakeAwayAuthoritiesAndLogin() {
        UUID accountId = UUID.randomUUID();
        HttpServletRequest request = org.mockito.Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = org.mockito.Mockito.mock(HttpServletResponse.class);
        DemoteRequest demoteRequest = new DemoteRequest(accountId, Set.of("ACCOUNT_CREATE"), request, response);
        ShortAccountResponseDto responseDto = new ShortAccountResponseDto(accountId, "test@example.com");

        when(managementService.takeAwayAuthoritiesById(eq(accountId), any())).thenReturn(responseDto);

        facade.demote(demoteRequest);

        verify(managementService).takeAwayAuthoritiesById(accountId, Set.of(Authority.ACCOUNT_CREATE));
        verify(loginService).login(any(LoginRequest.class), eq(request), eq(response));
    }
}
