package com.specialist.user.services;

import com.specialist.contracts.user.ProfileType;

import java.util.UUID;

public interface ProfileDeleteService {
    void deleteById(UUID id);
    ProfileType getType();
}
