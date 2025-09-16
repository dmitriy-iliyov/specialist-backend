package com.specialist.profile.services;

import com.specialist.profile.models.dtos.PrivateSpecialistResponseDto;
import com.specialist.profile.models.dtos.PublicSpecialistResponseDto;

import java.util.UUID;

public interface SpecialistProfileAggregator {
    PrivateSpecialistResponseDto findPrivateById(UUID id);

    PublicSpecialistResponseDto findPublicById(UUID id);
}
