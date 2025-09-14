package com.specialist.user.services.system;

import com.specialist.contracts.user.ProfileType;

import java.util.UUID;

public interface SystemEmailUpdateService {
    void updateById(UUID id, String email);
    ProfileType getType();
}
