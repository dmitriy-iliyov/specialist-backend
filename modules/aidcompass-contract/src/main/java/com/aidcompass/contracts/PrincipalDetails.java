package com.aidcompass.contracts;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public interface PrincipalDetails {
    UUID getUserId();
    Collection<? extends GrantedAuthority> getAuthorities();
}
