package com.aidcompass.core.security.domain.service;

import com.aidcompass.core.security.domain.BaseUserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class ServiceUserDetails implements BaseUserDetails {

    @Getter
    private final UUID id;
    private final String serviceName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private final boolean isLocked;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.serviceName;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }
}
