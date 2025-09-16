package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.models.dtos.PrivateUserResponseDto;
import com.specialist.profile.models.dtos.PublicUserResponseDto;
import com.specialist.profile.models.dtos.UserUpdateDto;
import com.specialist.profile.models.enums.ScopeType;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface UserProfileService {
    PrivateUserResponseDto update(UserUpdateDto dto);

    PrivateUserResponseDto findPrivateById(UUID id);

    PublicUserResponseDto findPublicById(UUID id);

    PageResponse<?> findAll(ScopeType scope, PageRequest page);

    void updateAvatarUrlById(UUID id, String avatarUrl);

    ProfileType getType();
}
