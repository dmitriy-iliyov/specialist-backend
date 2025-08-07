package com.specialist.auth.domain.authority;

import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.service_account.models.ServiceAccountEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "authorities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    @ManyToMany(mappedBy = "authorities")
    private Set<AccountEntity> accounts;

    @ManyToMany(mappedBy = "authorities")
    private Set<ServiceAccountEntity> serviceAccounts;

    public AuthorityEntity(Authority authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority.name();
    }

    public Authority getAuthorityAsEnum() {
        return authority;
    }
}
