package com.specialist.profile.services;

import com.specialist.profile.models.ProfileFilter;
import com.specialist.profile.models.dtos.PrivateSpecialistResponseDto;
import com.specialist.profile.models.dtos.PublicSpecialistResponseDto;
import com.specialist.profile.models.dtos.SpecialistUpdateDto;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface SpecialistProfileService {
    PrivateSpecialistResponseDto update(SpecialistUpdateDto dto);

    PrivateSpecialistResponseDto findPrivateById(UUID id);

    PublicSpecialistResponseDto findPublicById(UUID id);

    void updateAvatarUrlById(UUID id, String avatarUrl);

    void approve(UUID id);

    PageResponse<PrivateSpecialistResponseDto> findAll(ProfileFilter filter);
}
