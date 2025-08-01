package com.aidcompass.auth.domain.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Transactional(readOnly = true)
    @Override
    public RoleEntity getReferenceByRole(Role role) {
        return repository.getReferenceByRole(role);
    }
}
