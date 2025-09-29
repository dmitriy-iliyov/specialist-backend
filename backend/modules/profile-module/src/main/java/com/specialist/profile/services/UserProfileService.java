package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.models.dtos.PrivateUserResponseDto;
import com.specialist.profile.models.dtos.UserUpdateDto;

import java.util.UUID;

public interface UserProfileService {
    PrivateUserResponseDto update(UserUpdateDto dto);

    void updateAvatarUrlById(UUID id, String avatarUrl);

    ProfileType getType();
}
