package com.specialist.auth.domain.refresh_token.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class RefreshTokenUserDetails implements UserDetails, RefreshTokenIdHolder {

    private final UUID id;
    private final UUID accountId;
    private final List<? extends  GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return accountId.toString();
    }

    @Override
    public String getUsername() {
        return accountId.toString();
    }
}
