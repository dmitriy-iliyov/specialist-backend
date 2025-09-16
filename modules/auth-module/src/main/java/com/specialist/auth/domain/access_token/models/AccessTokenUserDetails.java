package com.specialist.auth.domain.access_token.models;

import com.specialist.auth.domain.refresh_token.models.RefreshTokenIdHolder;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.exceptions.RoleNotFoundException;
import com.specialist.contracts.auth.PrincipalDetails;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Slf4j
public class AccessTokenUserDetails implements UserDetails, RefreshTokenIdHolder, PrincipalDetails {

    private UUID id;
    private List<? extends GrantedAuthority> authorities;
    private UUID accountId;

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
        return accountId.toString();
    }

    public Role getRole() {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .findFirst()
                .map(Role::fromJson)
                .orElseThrow(() -> {
                    log.error("Account with accountId {} hasn't role.", accountId);
                    return new RoleNotFoundException();
                });
    }

    @Override
    public String getStringRole() {
        return getRole().toString();
    }
}
