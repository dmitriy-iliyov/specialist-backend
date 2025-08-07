package com.specialist.auth.infrastructure;

import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.role.RoleEntity;
import com.specialist.auth.domain.role.RoleRepository;
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
