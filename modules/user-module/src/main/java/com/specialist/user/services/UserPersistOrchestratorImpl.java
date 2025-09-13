package com.specialist.user.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.contracts.user.UserCompleteEvent;
import com.specialist.user.exceptions.NullUserEmailException;
import com.specialist.user.models.dtos.*;
import com.specialist.user.repositories.AvatarStorage;
import com.specialist.user.services.system.UserEmailService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserPersistOrchestratorImpl implements UserPersistOrchestrator {

    private final UserPersistService<UserCreateDto, PrivateUserResponseDto> userPersistService;
    private final UserPersistService<SpecialistCreateDto, PrivateSpecialistResponseDto> specialistPersistService;
    private final ObjectMapper mapper;
    private final AvatarStorage avatarStorage;
    private final Validator validator;
    private final ApplicationEventPublisher eventPublisher;
    private final UserEmailService emailService;

    @Override
    public BasePrivateResponseDto save(CreateRequest request) throws JsonProcessingException {
        BasePrivateResponseDto responseDto = switch (request.type()) {
            case USER -> {
                UserCreateDto dto = mapper.readValue(request.rawDto(), UserCreateDto.class);
                prepare(dto, request);
                yield userPersistService.save(dto);
            }
            case SPECIALIST -> {
                SpecialistCreateDto dto = mapper.readValue(request.rawDto(), SpecialistCreateDto.class);
                prepare(dto, request);
                yield specialistPersistService.save(dto);
            }
        };
        eventPublisher.publishEvent(
                new UserCompleteEvent(responseDto.getId(), responseDto.getType(), request.request(), request.response())
        );
        return responseDto;
    }

    private void prepare(UserCreateDto dto, CreateRequest request) {
        dto.setId(request.accountId());
        dto.setAvatar(request.avatar());
        Set<ConstraintViolation<BaseDto>> errors = validator.validate(dto);
        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
        String avatarUrl = avatarStorage.save(dto.getAvatar(), dto.getId());
        dto.setAvatarUrl(avatarUrl);
        String email = emailService.findById(dto.getId());
        if (email == null) {
            throw new NullUserEmailException();
        }
        dto.setEmail(email);
    }
}
