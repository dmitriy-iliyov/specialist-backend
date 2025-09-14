package com.specialist.user.services;

import com.specialist.contracts.user.ProfileType;
import com.specialist.user.models.dtos.PrivateUserResponseDto;
import com.specialist.user.models.dtos.PublicUserResponseDto;
import com.specialist.user.models.dtos.UserUpdateDto;
import com.specialist.user.models.enums.ScopeType;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface UserService {
    PrivateUserResponseDto update(UserUpdateDto dto);

    PrivateUserResponseDto findPrivateById(UUID id);

    PublicUserResponseDto findPublicById(UUID id);

    PageResponse<?> findAll(ScopeType scope, PageRequest page);

    void updateAvatarUrlById(UUID id, String avatarUrl);

    ProfileType getType();
}
