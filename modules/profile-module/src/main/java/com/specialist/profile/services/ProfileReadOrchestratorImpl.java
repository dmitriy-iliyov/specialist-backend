package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.dto.BaseResponseDto;
import com.specialist.profile.exceptions.UserNotFoundByIdException;
import com.specialist.profile.models.dtos.BasePrivateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileReadOrchestratorImpl implements ProfileReadOrchestrator {

    private final UserProfileService userProfileService;
    private final SpecialistReadOrchestrator specialistOrchestrator;

    @Override
    public BasePrivateResponseDto findPrivateById(UUID id, ProfileType type) {
        return switch (type) {
            case USER -> userProfileService.findPrivateById(id);
            case SPECIALIST -> specialistOrchestrator.findPrivateById(id);
        };
    }

    @Override
    public BaseResponseDto findPublicById(UUID id) {
        try {
            return userProfileService.findPublicById(id);
        } catch (UserNotFoundByIdException e) {
            return specialistOrchestrator.findPublicById(id);
        }
    }
}
