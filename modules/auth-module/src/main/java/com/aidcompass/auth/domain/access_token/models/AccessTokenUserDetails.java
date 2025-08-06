package com.aidcompass.auth.domain.access_token.models;

import com.aidcompass.contracts.auth.PrincipalDetails;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Builder
public class AccessTokenUserDetails implements UserDetails, PrincipalDetails {

    @Getter
    private UUID id;
    private List<? extends GrantedAuthority> authorities;
    @Getter
    private UUID userId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId.toString();
    }
}
