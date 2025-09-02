package com.specialist.auth.domain.role;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.specialist.auth.exceptions.UnsupportedRoleException;

import java.util.Arrays;

public enum Role {
    ROLE_USER, ROLE_ADMIN, ROLE_SERVICE, ROLE_SPECIALIST;

    @JsonCreator
    public static Role fromJson(String json) {
        return Arrays.stream(Role.values())
                .filter(role -> role.name().equalsIgnoreCase(json))
                .findFirst()
                .orElseThrow(UnsupportedRoleException::new);
    }
}
