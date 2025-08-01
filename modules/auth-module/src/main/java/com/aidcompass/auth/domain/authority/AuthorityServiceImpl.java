package com.aidcompass.auth.domain.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<AuthorityEntity> getReferenceAllByAuthorityIn(List<Authority> authorities) {
        return repository.getReferenceAllByAuthorityIn(authorities);
    }

    @Override
    public Map<UUID, List<Authority>> findAllByAccountIdIn(Set<UUID> accountIds) {
        List<Object[]> pairs = repository.findAllByAccountIdIn(accountIds);
        Map<UUID, List<Authority>> authoritiesMap = new HashMap<>();
        for (Object [] pair : pairs) {
            UUID id = (UUID) pair[0];
            AuthorityEntity authority = (AuthorityEntity) pair[1];
            authoritiesMap.computeIfAbsent(id, k -> new ArrayList<>())
                    .add(authority.getAuthorityAsEnum());
        }
        return authoritiesMap;
    }
}
