package com.specialist.user.services;

import com.specialist.contracts.user.ProfileType;
import com.specialist.contracts.user.dto.BaseResponseDto;
import com.specialist.user.models.dtos.BasePrivateResponseDto;

import java.util.UUID;

public interface ProfileReadOrchestrator {
    BasePrivateResponseDto findPrivateById(UUID id, ProfileType type);
    BaseResponseDto findPublicById(UUID id);
}
