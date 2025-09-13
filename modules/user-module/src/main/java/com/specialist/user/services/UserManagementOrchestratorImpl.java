package com.specialist.user.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.contracts.user.UserType;
import com.specialist.user.models.dtos.*;
import com.specialist.user.repositories.AvatarStorage;
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
public class UserManagementOrchestratorImpl implements UserManagementOrchestrator {

    private final UserService userService;
    private final SpecialistService specialistService;
    private final AvatarStorage avatarStorage;
    private final Validator validator;
    private final ObjectMapper mapper;

    @Override
    public BasePrivateResponseDto update(UpdateRequest request) throws JsonProcessingException {
        return switch (request.type()) {
            case USER -> {
                UserUpdateDto dto = mapper.readValue(request.rawDto(), UserUpdateDto.class);
                prepare(dto, request);
                yield userService.update(dto);
            } case SPECIALIST -> {
                SpecialistUpdateDto dto = mapper.readValue(request.rawDto(), SpecialistUpdateDto.class);
                prepare(dto, request);
                yield specialistService.update(dto);
            }
        };
    }

    @Override
    public String updateAvatar(UUID id, UserType type, MultipartFile avatar) {
        String avatarUrl = avatarStorage.save(avatar, id);
        switch (type) {
            case USER -> userService.updateAvatarUrlById(id, avatarUrl);
            case SPECIALIST -> specialistService.updateAvatarUrlById(id, avatarUrl);
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
