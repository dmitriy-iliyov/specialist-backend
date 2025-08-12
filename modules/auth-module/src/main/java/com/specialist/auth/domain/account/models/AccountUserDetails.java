package com.specialist.auth.domain.account.models;

import com.specialist.auth.core.models.BaseUserDetails;
import com.specialist.auth.domain.auth_provider.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class AccountUserDetails implements UserDetails, CredentialsContainer, BaseUserDetails {

    private UUID id;
    private String email;
    private String password;
    private Provider provider;
    private List<? extends GrantedAuthority> authorities;
    private boolean isLocked;
    private boolean isEnabled;

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
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
