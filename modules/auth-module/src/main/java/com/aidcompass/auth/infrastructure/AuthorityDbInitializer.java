package com.aidcompass.auth.infrastructure;

import com.aidcompass.auth.domain.authority.Authority;
import com.aidcompass.auth.domain.authority.AuthorityEntity;
import com.aidcompass.auth.domain.authority.AuthorityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorityDbInitializer {

    private final AuthorityRepository repository;

    @PostConstruct
    public void setUp() {
        List<AuthorityEntity> authorities = Arrays.stream(Authority.values()).map(AuthorityEntity::new).toList();
        repository.saveAll(authorities);
    }
}
