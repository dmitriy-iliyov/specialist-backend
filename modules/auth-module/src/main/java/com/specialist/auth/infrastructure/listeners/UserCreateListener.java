package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.SessionCookieManager;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityServiceImpl;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.role.RoleMapper;
import com.specialist.auth.exceptions.UnknownRoleException;
import com.specialist.contracts.auth.UserCompleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public final class UserCreateListener {

    private final RoleMapper mapper;
    private final AccountService accountService;
    private final UserDetailsService userDetailsService;
    private final SessionCookieManager sessionCookieManager;

    public UserCreateListener(RoleMapper mapper, AccountService accountService,
                              @Qualifier("accountUserDetailsService") UserDetailsService userDetailsService,
                              SessionCookieManager sessionCookieManager) {
        this.mapper = mapper;
        this.accountService = accountService;
        this.userDetailsService = userDetailsService;
        this.sessionCookieManager = sessionCookieManager;
    }

    @EventListener
    public void listen(UserCompleteEvent event) {
        Role role = mapper.toRole(event.userType());
        Set<Authority> authorities;
        if (role.equals(Role.ROLE_USER)) {
            authorities = new HashSet<>(AuthorityServiceImpl.DEFAULT_POST_REGISTER_USER_AUTHORITIES);
        } else if (role.equals(Role.ROLE_SPECIALIST)) {
            authorities = Set.of(Authority.REVIEW_CREATE_UPDATE);
        }
        else {
            log.error("Unknown role {}", role);
            throw new UnknownRoleException();
        }
        ShortAccountResponseDto dto = accountService.updateRoleAndAuthoritiesById(event.userId(), role, authorities);
        AccountUserDetails userDetails = (AccountUserDetails) userDetailsService.loadUserByUsername(dto.email());
        sessionCookieManager.create(userDetails, event.request(), event.response());
    }
}
