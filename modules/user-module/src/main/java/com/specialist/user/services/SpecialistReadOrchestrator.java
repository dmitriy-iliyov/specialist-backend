package com.specialist.user.services;

import com.specialist.user.models.dtos.PrivateSpecialistResponseDto;
import com.specialist.user.models.dtos.PublicSpecialistResponseDto;

import java.util.UUID;

public interface SpecialistReadOrchestrator {
    PrivateSpecialistResponseDto findPrivateById(UUID id);

    PublicSpecialistResponseDto findPublicById(UUID id);
}
