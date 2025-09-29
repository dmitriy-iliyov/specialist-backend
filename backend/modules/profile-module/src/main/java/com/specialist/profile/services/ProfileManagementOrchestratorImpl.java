package com.specialist.profile.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.models.dtos.*;
import com.specialist.profile.repositories.AvatarStorage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileManagementOrchestratorImpl implements ProfileManagementOrchestrator {

    private final UserProfileService userProfileService;
    private final SpecialistProfileService specialistProfileService;
    private final AvatarStorage avatarStorage;
    private final Validator validator;
    private final ObjectMapper mapper;

    @Override
    public BasePrivateResponseDto update(UpdateRequest request) throws JsonProcessingException {
        return switch (request.type()) {
            case USER -> {
                UserUpdateDto dto = mapper.readValue(request.rawDto(), UserUpdateDto.class);
                prepare(dto, request);
                yield userProfileService.update(dto);
            } case SPECIALIST -> {
                SpecialistUpdateDto dto = mapper.readValue(request.rawDto(), SpecialistUpdateDto.class);
                prepare(dto, request);
                yield specialistProfileService.update(dto);
            }
        };
    }

    @Override
    public String updateAvatar(UUID id, ProfileType type, MultipartFile avatar) {
        String avatarUrl = avatarStorage.save(avatar, id);
        switch (type) {
            case USER -> userProfileService.updateAvatarUrlById(id, avatarUrl);
            case SPECIALIST -> specialistProfileService.updateAvatarUrlById(id, avatarUrl);
        }
        return avatarUrl;
    }

    private void prepare(UserUpdateDto dto, UpdateRequest request) {
        dto.setId(request.accountId());
        dto.setAvatar(request.avatar());
        validate(dto);
        String avatarUrl = avatarStorage.save(dto.getAvatar(), dto.getId());
        dto.setAvatarUrl(avatarUrl);
    }

    private void validate(BaseDto dto) {
        Set<ConstraintViolation<BaseDto>> errors = validator.validate(dto);
        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
    }
}
