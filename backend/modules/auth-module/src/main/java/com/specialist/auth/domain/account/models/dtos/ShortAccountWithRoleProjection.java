package com.specialist.auth.domain.account.models.dtos;

import com.specialist.auth.domain.role.Role;

import java.util.UUID;

public interface ShortAccountWithRoleProjection {
    UUID getId();
    Role getRole();
}
