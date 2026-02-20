package com.specialist.profile.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.contracts.profile.ProfileCreateEvent;
import com.specialist.picture.PictureStorage;
import com.specialist.profile.exceptions.NullUserEmailException;
import com.specialist.profile.models.dtos.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProfilePersistOrchestratorImpl implements ProfilePersistOrchestrator {

    private final ProfilePersistService<UserCreateDto, PrivateUserResponseDto> profilePersistService;
    private final ProfilePersistService<SpecialistCreateDto, PrivateSpecialistResponseDto> specialistPersistService;
    private final ObjectMapper mapper;
    private final PictureStorage pictureStorage;
    private final Validator validator;
    private final ApplicationEventPublisher eventPublisher;
    private final ProfileReadService profileReadService;

    public ProfilePersistOrchestratorImpl(ProfilePersistService<UserCreateDto,
                                          PrivateUserResponseDto> profilePersistService,
                                          ProfilePersistService<SpecialistCreateDto,
                                          PrivateSpecialistResponseDto> specialistPersistService,
                                          ObjectMapper mapper,
                                          @Qualifier("profilePictureStorage") PictureStorage pictureStorage,
                                          Validator validator,
                                          ApplicationEventPublisher eventPublisher,
                                          ProfileReadService profileReadService) {
        this.profilePersistService = profilePersistService;
        this.specialistPersistService = specialistPersistService;
        this.mapper = mapper;
        this.pictureStorage = pictureStorage;
        this.validator = validator;
        this.eventPublisher = eventPublisher;
        this.profileReadService = profileReadService;
    }

    @Override
    public BasePrivateResponseDto save(CreateRequest request) throws JsonProcessingException {
        BasePrivateResponseDto responseDto = switch (request.type()) {
            case USER -> {
                UserCreateDto dto = mapper.readValue(request.rawDto(), UserCreateDto.class);
                prepare(dto, request);
                yield profilePersistService.save(dto);
            }
            case SPECIALIST -> {
                SpecialistCreateDto dto = mapper.readValue(request.rawDto(), SpecialistCreateDto.class);
                prepare(dto, request);
                yield specialistPersistService.save(dto);
            }
        };
        eventPublisher.publishEvent(
                new ProfileCreateEvent(responseDto.getId(), responseDto.getType(), request.request(), request.response())
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
        String avatarUrl = pictureStorage.save(dto.getAvatar(), dto.getId());
        dto.setAvatarUrl(avatarUrl);
        String email = profileReadService.findEmailById(dto.getId());
        if (email == null) {
            throw new NullUserEmailException();
        }
        dto.setEmail(email);
    }
}
