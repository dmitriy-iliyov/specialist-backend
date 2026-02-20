package com.specialist.auth.domain.authority;

import java.util.List;

public interface AuthorityCacheService {
    List<Long> getAuthoritiesIds(List<Authority> authorities);
}
