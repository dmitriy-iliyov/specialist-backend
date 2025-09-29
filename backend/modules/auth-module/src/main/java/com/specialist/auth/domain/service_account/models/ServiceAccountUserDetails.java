package com.specialist.auth.domain.service_account.models;

import com.specialist.auth.core.BaseUserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ServiceAccountUserDetails implements UserDetails, CredentialsContainer, BaseUserDetails {

    @Getter
    private UUID id;
    private String secret;
    private List<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return secret;
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public void eraseCredentials() {
        secret = null;
    }
}
