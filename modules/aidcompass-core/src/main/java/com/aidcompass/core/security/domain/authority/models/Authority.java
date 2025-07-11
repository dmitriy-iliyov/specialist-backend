package com.aidcompass.core.security.domain.authority.models;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    ROLE_ANONYMOUS,
    ROLE_UNCONFIRMED_USER,
    ROLE_USER,
    ROLE_CUSTOMER,
    ROLE_DOCTOR,
    ROLE_JURIST,
    ROLE_ADMIN,
    ROLE_SCHEDULE_TASK_SERVICE,
    ROLE_PROMETHEUS_SERVICE;

    @Override
    public String getAuthority() {
        return name();
    }
}
