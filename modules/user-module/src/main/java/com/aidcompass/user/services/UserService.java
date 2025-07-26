package com.aidcompass.user.services;

import com.aidcompass.contracts.user.PublicUserResponseDto;
import com.aidcompass.user.models.ScopeType;
import com.aidcompass.user.models.dto.PrivateUserResponseDto;
import com.aidcompass.user.models.dto.UserCreateDto;
import com.aidcompass.user.models.dto.UserUpdateDto;
import com.aidcompass.utils.pagination.PageRequest;
import com.aidcompass.utils.pagination.PageResponse;

import java.util.UUID;

public interface UserService {
    PrivateUserResponseDto save(UserCreateDto dto);

    PrivateUserResponseDto findPrivateById(UUID id);

    PrivateUserResponseDto update(UserUpdateDto dto);

    PublicUserResponseDto findPublicById(UUID id);

    PageResponse<?> findAll(ScopeType scope, PageRequest page);

    void deleteById(UUID id);

    void updateAvatarUrlById(UUID id, String avatarUrl);
}
