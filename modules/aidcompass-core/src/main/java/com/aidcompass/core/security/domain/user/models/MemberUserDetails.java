package com.aidcompass.core.security.domain.user.models;

import com.aidcompass.core.security.domain.BaseUserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MemberUserDetails implements BaseUserDetails {

    @Getter
    private UUID id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isExpired;
    private boolean isLocked;

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
        return !isExpired;
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
        return true;
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }
}
