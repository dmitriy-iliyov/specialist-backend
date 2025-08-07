package com.specialist.auth.domain.authority;

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

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, List<Authority>> findAllByAccountIdIn(Set<UUID> accountIds) {
        List<Object[]> pairs = repository.findAllByAccountIdIn(accountIds);
        return toMap(pairs);
    }

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, List<Authority>> findAllByServiceAccountIdIn(Set<UUID> serviceAccountIds) {
        List<Object[]> pairs = repository.findAllByServiceAccountIdIn(serviceAccountIds);
        return toMap(pairs);
    }

    private Map<UUID, List<Authority>> toMap(List<Object[]> pairs) {
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
