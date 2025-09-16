package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.web.AccountLoginService;
import com.specialist.auth.core.web.LoginRequest;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.contracts.auth.DemoteRequest;
import com.specialist.contracts.auth.SystemAccountDemoteFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SystemAccountDemoteFacadeImpl implements SystemAccountDemoteFacade {

    private final SystemAccountManagementService managementService;
    private final AccountLoginService loginService;

    public SystemAccountDemoteFacadeImpl(SystemAccountManagementService managementService,
                                         @Qualifier("implicitAccountLoginService") AccountLoginService loginService) {
        this.managementService = managementService;
        this.loginService = loginService;
    }

    @Override
    public void demote(DemoteRequest request) {
        Set<Authority> authorities = request.authorities().stream()
                .map(Authority::fromJson)
                .collect(Collectors.toSet());
        ShortAccountResponseDto dto = managementService.takeAwayAuthoritiesById(request.accountId(), authorities);
        loginService.login(new LoginRequest(dto.email(), null), request.request(), request.response());
    }
}
