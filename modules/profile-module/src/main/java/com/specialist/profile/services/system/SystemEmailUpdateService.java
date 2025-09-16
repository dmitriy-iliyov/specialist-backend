package com.specialist.profile.services.system;

import com.specialist.contracts.profile.ProfileType;

import java.util.UUID;

public interface SystemEmailUpdateService {
    void updateById(UUID id, String email);
    ProfileType getType();
}
