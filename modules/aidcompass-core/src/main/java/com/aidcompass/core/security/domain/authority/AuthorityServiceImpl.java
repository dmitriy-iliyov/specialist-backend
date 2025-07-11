package com.aidcompass.core.security.domain.authority;

import com.aidcompass.core.security.domain.authority.models.Authority;
import com.aidcompass.core.security.domain.authority.models.AuthorityEntity;
import com.aidcompass.core.security.exceptions.not_found.AuthorityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository repository;


    @Override
    @Transactional(readOnly = true)
    public AuthorityEntity findByAuthority(Authority authority) {
        return repository.findByAuthority(authority).orElseThrow(AuthorityNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorityEntity> toAuthorityEntityList(List<Authority> authorities) {
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        for (Authority authority: authorities) {
            authorityEntities.add(repository.findByAuthority(authority).orElseThrow(
                    AuthorityNotFoundException::new)
            );
        }
        return authorityEntities;
    }
}
