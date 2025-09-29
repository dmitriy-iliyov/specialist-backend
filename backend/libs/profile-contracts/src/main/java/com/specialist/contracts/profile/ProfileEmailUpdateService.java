package com.specialist.contracts.profile;

import java.util.UUID;

public interface ProfileEmailUpdateService {
    void updateById(ProfileType type, UUID id, String email);
}
