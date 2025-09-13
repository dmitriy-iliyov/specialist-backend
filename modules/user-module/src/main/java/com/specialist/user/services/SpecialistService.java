package com.specialist.user.services;

import com.specialist.user.models.dtos.PrivateSpecialistResponseDto;
import com.specialist.user.models.dtos.PublicSpecialistResponseDto;
import com.specialist.user.models.dtos.SpecialistUpdateDto;

import java.util.UUID;

public interface SpecialistService {
    PrivateSpecialistResponseDto update(SpecialistUpdateDto dto);

    PrivateSpecialistResponseDto findPrivateById(UUID id);

    PublicSpecialistResponseDto findPublicById(UUID id);

    void updateAvatarUrlById(UUID id, String avatarUrl);

    void approve(UUID id);
}
