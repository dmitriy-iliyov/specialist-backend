package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.dto.BaseResponseDto;
import com.specialist.profile.models.ProfileFilter;
import com.specialist.profile.models.dtos.BasePrivateResponseDto;
import com.specialist.profile.models.enums.ScopeType;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface ProfileReadStrategy {
    ProfileType getType();

    BasePrivateResponseDto findPrivateById(UUID id);

    BaseResponseDto findPublicById(UUID id);

    PageResponse<?> findAll(ScopeType scope, ProfileFilter filter);
}
