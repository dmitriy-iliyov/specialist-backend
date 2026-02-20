package com.specialist.specialistdirectory.domain.specialist.models.dtos;

import com.specialist.contracts.profile.ProfileType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public record SpecialistCreateRequest(
        UUID creatorId,
        ProfileType profileType,
        SpecialistCreateDto dto,
        HttpServletRequest request,
        HttpServletResponse response
) { }
