package com.aidcompass.core.security.domain.token.models;

import com.aidcompass.contracts.PrincipalDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;


@Data
@AllArgsConstructor
public class TokenUserDetails implements UserDetails, PrincipalDetails {

    private UUID userId;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Token token;

    public static TokenUserDetails build(Token token){
        return new TokenUserDetails(
                token.getSubjectId(),
                null,
                token.getAuthorities().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList(),
                token);
    }

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
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
