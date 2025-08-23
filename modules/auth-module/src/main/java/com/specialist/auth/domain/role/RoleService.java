package com.specialist.auth.domain.role;

import java.util.List;

public interface RoleService {
    RoleEntity getReferenceByRole(Role role);

    List<RoleEntity> findAll();
}
