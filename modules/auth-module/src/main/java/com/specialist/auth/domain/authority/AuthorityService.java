package com.specialist.auth.domain.authority;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface AuthorityService {
    List<AuthorityEntity> getReferenceAllByAuthorityIn(List<Authority> authorities);

    Map<UUID, List<Authority>> findAllByAccountIdIn(Set<UUID> accountIds);

    Map<UUID, List<Authority>> findAllByServiceAccountIdIn(Set<UUID> serviceAccountIds);

    List<AuthorityEntity> findAll();
}
