package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;

import java.util.UUID;

public interface EmailUpdateStrategy {
    void updateById(UUID id, String email);
    ProfileType getType();
}
