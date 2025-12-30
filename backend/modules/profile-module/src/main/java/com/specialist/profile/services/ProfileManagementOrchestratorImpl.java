package com.specialist.profile.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.picture.PictureStorage;
import com.specialist.profile.models.dtos.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;

@Service
public class ProfileManagementOrchestratorImpl implements ProfileManagementOrchestrator {

    private final UserProfileService userProfileService;
    private final SpecialistProfileService specialistProfileService;
    private final PictureStorage pictureStorage;
    private final Validator validator;
    private final ObjectMapper mapper;

    public ProfileManagementOrchestratorImpl(UserProfileService userProfileService,
                                             SpecialistProfileService specialistProfileService,
                                             @Qualifier("profilePictureStorage") PictureStorage pictureStorage,
                                             Validator validator, ObjectMapper mapper) {
        this.userProfileService = userProfileService;
        this.specialistProfileService = specialistProfileService;
        this.pictureStorage = pictureStorage;
        this.validator = validator;
        this.mapper = mapper;
    }

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
        String avatarUrl = pictureStorage.save(avatar, id);
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
        String avatarUrl = pictureStorage.save(dto.getAvatar(), dto.getId());
        dto.setAvatarUrl(avatarUrl);
    }

    private void validate(BaseDto dto) {
        Set<ConstraintViolation<BaseDto>> errors = validator.validate(dto);
        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
    }
}
