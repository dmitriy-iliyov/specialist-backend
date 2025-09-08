package com.specialist.auth.infrastructure;

import com.specialist.auth.core.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.authority.AuthorityRepository;
import com.specialist.auth.domain.authority.AuthorityService;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.role.RoleRepository;
import com.specialist.auth.domain.role.RoleService;
import com.specialist.utils.UuidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Profile("init-admin")
@Component
@RequiredArgsConstructor
public class AdminDbInitializer {

    private final RoleService roleService;
    private final AuthorityService authorityService;
    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void setUp() {
        List<AuthorityEntity> authorityEntityList = authorityService.getReferenceAllByAuthorityIn(
                List.of(Authority.ACCOUNT_MANAGER, Authority.SERVICE_ACCOUNT_MANAGER,
                        Authority.TYPE_MANAGEMENT, Authority.TYPE_TRANSLATE_MANAGEMENT,
                        Authority.SPECIALIST_MANAGEMENT, Authority.REVIEW_MANAGEMENT)
        );
        repository.save(new AccountEntity(
                UuidUtils.generateV7(),
                "",
                passwordEncoder.encode(""),
                Provider.LOCAL,
                roleService.getReferenceByRole(Role.ROLE_ADMIN),
                authorityEntityList,
                false,
                null,
                null,
                true,
                null,
                Instant.now(),
                Instant.now()
        ));
    }
}
