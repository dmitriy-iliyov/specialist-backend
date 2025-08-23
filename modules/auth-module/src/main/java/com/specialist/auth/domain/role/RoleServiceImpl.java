package com.specialist.auth.domain.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final RoleCacheService cacheService;

    @Transactional(readOnly = true)
    @Override
    public RoleEntity getReferenceByRole(Role role) {
        Long id = cacheService.getRoleId(role);
        if (id != null) {
            return repository.getReferenceById(id);
        }
        return repository.getReferenceByRole(role);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoleEntity> findAll() {
        return repository.findAll();
    }
}
