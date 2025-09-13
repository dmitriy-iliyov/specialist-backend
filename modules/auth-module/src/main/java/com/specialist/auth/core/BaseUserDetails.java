package com.specialist.auth.core;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public interface BaseUserDetails {
    UUID getId();
    Collection<? extends GrantedAuthority> getAuthorities();
}
