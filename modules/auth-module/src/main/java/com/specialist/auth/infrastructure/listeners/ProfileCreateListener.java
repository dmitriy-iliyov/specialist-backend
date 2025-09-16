package com.specialist.auth.infrastructure.listeners;

import com.specialist.auth.core.web.AccountLoginService;
import com.specialist.auth.core.web.LoginRequest;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityServiceImpl;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.exceptions.UnknownRoleException;
import com.specialist.contracts.profile.ProfileCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public final class ProfileCreateListener {

    private final AccountService accountService;
    private final AccountLoginService loginService;

    public ProfileCreateListener(AccountService accountService,
                                 @Qualifier("implicitAccountLoginService")
                                 AccountLoginService loginService) {
        this.accountService = accountService;
        this.loginService = loginService;
    }

    @EventListener
    public void listen(ProfileCreateEvent event) {
        Role role = Role.fromJson(event.profileType().getStringRole());
        Set<Authority> authorities;
        if (role.equals(Role.ROLE_USER)) {
            authorities = new HashSet<>(AuthorityServiceImpl.DEFAULT_POST_REGISTER_USER_AUTHORITIES);
        } else if (role.equals(Role.ROLE_SPECIALIST)) {
            authorities = Set.of(Authority.REVIEW_CREATE_UPDATE, Authority.SPECIALIST_CREATE, Authority.SPECIALIST_UPDATE);
        } else {
            log.error("Unknown role {}", role);
            throw new UnknownRoleException();
        }
        ShortAccountResponseDto dto = accountService.updateRoleAndAuthoritiesById(event.accountId(), role, authorities);
        loginService.login(new LoginRequest(dto.email(), null), event.request(), event.response());
    }
}
