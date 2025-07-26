package com.aidcompass.core.security.domain.authority.models;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    ROLE_USER,
    ROLE_ADMIN,
    ROLE_PROMETHEUS_SERVICE;

    @Override
    public String getAuthority() {
        return name();
    }
}
