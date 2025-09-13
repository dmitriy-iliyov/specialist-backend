package com.specialist.user.services;

import com.specialist.contracts.user.UserType;
import com.specialist.contracts.user.dto.BaseResponseDto;
import com.specialist.user.exceptions.UserNotFoundByIdException;
import com.specialist.user.models.dtos.BasePrivateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserReadOrchestratorImpl implements UserReadOrchestrator {

    private final UserService userService;
    private final SpecialistReadOrchestrator specialistOrchestrator;

    @Override
    public BasePrivateResponseDto findPrivateById(UUID id, UserType type) {
        return switch (type) {
            case USER -> userService.findPrivateById(id);
            case SPECIALIST -> specialistOrchestrator.findPrivateById(id);
        };
    }

    @Override
    public BaseResponseDto findPublicById(UUID id) {
        try {
            return userService.findPublicById(id);
        } catch (UserNotFoundByIdException e) {
            return specialistOrchestrator.findPublicById(id);
        }
    }
}
