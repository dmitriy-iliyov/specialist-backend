package com.specialist.user.services;

import com.specialist.contracts.user.UserType;
import com.specialist.contracts.user.dto.BaseResponseDto;
import com.specialist.user.models.dtos.BasePrivateResponseDto;

import java.util.UUID;

public interface UserReadOrchestrator {
    BasePrivateResponseDto findPrivateById(UUID id, UserType type);
    BaseResponseDto findPublicById(UUID id);
}
