package com.specialist.user.services;

import com.specialist.contracts.user.PublicUserResponseDto;
import com.specialist.user.models.dtos.BaseUserDto;
import com.specialist.user.models.dtos.PrivateUserResponseDto;
import com.specialist.user.models.dtos.UserUpdateDto;
import com.specialist.user.models.enums.ScopeType;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface UserService {
    PrivateUserResponseDto save(BaseUserDto dto);

    PrivateUserResponseDto update(UserUpdateDto dto, EmailChangeHandler handler);

    boolean existsById(UUID id);

    PrivateUserResponseDto findPrivateById(UUID id);

    PublicUserResponseDto findPublicById(UUID id);

    PageResponse<?> findAll(ScopeType scope, PageRequest page);

    void deleteById(UUID id);

    void updateAvatarUrlById(UUID id, String avatarUrl);
}
