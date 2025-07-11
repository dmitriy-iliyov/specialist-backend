package com.aidcompass.core.security.domain.authority;

import com.aidcompass.core.security.domain.authority.models.Authority;
import com.aidcompass.core.security.domain.authority.models.AuthorityEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Profile("dev")
@Component
@RequiredArgsConstructor
public class AuthorityDatabaseInitializer {

    private final AuthorityRepository authorityRepository;


    @PostConstruct
    @Transactional
    public void init() {
        for (Authority authority : Authority.values()) {
            authorityRepository.findByAuthority(authority)
                    .orElseGet(() -> {
                        AuthorityEntity newAuthority = new AuthorityEntity();
                        newAuthority.setAuthority(authority);
                        return authorityRepository.save(newAuthority);
                    });
        }
    }
}

