package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.dto.BaseResponseDto;
import com.specialist.profile.models.dtos.BasePrivateResponseDto;

import java.util.UUID;

public interface ProfileReadOrchestrator {
    BasePrivateResponseDto findPrivateById(UUID id, ProfileType type);
    BaseResponseDto findPublicById(UUID id);
}
