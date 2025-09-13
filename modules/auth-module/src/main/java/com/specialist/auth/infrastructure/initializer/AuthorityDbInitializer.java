package com.specialist.auth.infrastructure.initializer;

import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.authority.AuthorityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Profile("initialize-db")
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
