package com.aidcompass.auth.infrastructure;

import com.aidcompass.auth.domain.role.Role;
import com.aidcompass.auth.domain.role.RoleEntity;
import com.aidcompass.auth.domain.role.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RoleDbInitializer {

    private final RoleRepository repository;

    @PostConstruct
    public void setUp() {
        repository.saveAll(Arrays.stream(Role.values()).map(RoleEntity::new).toList());
    }
}
