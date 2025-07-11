package com.aidcompass.core.security.domain.authority;


import com.aidcompass.core.security.domain.authority.models.Authority;
import com.aidcompass.core.security.domain.authority.models.AuthorityEntity;

import java.util.List;

public interface AuthorityService {

    AuthorityEntity findByAuthority(Authority authority);

    List<AuthorityEntity> toAuthorityEntityList(List<Authority> authorities);
}
