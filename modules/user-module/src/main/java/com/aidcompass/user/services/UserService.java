package com.aidcompass.user.services;

import com.aidcompass.contracts.user.PublicUserResponseDto;
import com.aidcompass.user.models.enums.ScopeType;
import com.aidcompass.user.models.dtos.PrivateUserResponseDto;
import com.aidcompass.user.models.dtos.UserCreateDto;
import com.aidcompass.user.models.dtos.UserUpdateDto;
import com.aidcompass.utils.pagination.PageRequest;
import com.aidcompass.utils.pagination.PageResponse;

import java.util.UUID;

public interface UserService {
    PrivateUserResponseDto save(UserCreateDto dto);

    PrivateUserResponseDto findPrivateById(UUID id);

    PrivateUserResponseDto update(UserUpdateDto dto, EmailChangeHandler handler);

    PublicUserResponseDto findPublicById(UUID id);

    PageResponse<?> findAll(ScopeType scope, PageRequest page);

    void deleteById(UUID id);

    void updateAvatarUrlById(UUID id, String avatarUrl);
}
