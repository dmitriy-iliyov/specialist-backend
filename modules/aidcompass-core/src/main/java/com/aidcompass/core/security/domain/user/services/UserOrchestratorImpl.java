package com.aidcompass.core.security.domain.user.services;

import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.core.facades.ContactServiceSyncOrchestrator;
import com.aidcompass.core.contact.core.models.dto.system.SystemConfirmationRequestDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactCreateDto;
import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.utils.uuid.UuidFactory;
import com.aidcompass.core.security.auth.dto.RecoveryRequestDto;
import com.aidcompass.core.security.domain.user.models.dto.*;
import com.aidcompass.core.security.exceptions.not_found.EmailNotFoundException;
import com.aidcompass.core.security.handlers.UserDeleteHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
@Slf4j
public class UserOrchestratorImpl implements UserOrchestrator {

    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;
    private final ContactServiceSyncOrchestrator synchronizationFacade;
    private final Validator validator;
    private final UserDeleteHandler userDeleteHandler;


    @Override
    public void save(UserRegistrationDto dto) {
        UUID id = UuidFactory.generate();
        if (userService.existsById(id)) {
            id = UuidFactory.generate();
            log.error("Id duplicated, id: {}", id);
        }
        synchronizationFacade.save(new SystemContactCreateDto(id, ContactType.EMAIL, dto.email()));
        unconfirmedUserService.save(id, dto);
    }

    @Transactional
    @Override
    public void confirmByEmail(String email) {
        Long emailId = synchronizationFacade.confirmContact(new SystemConfirmationRequestDto(email));
        try {
            userService.confirmByEmail(email, emailId);
        } catch (BaseNotFoundException e) {
            SystemUserDto systemUserDto = unconfirmedUserService.systemFindByEmail(email, emailId);
            userService.save(systemUserDto);
            unconfirmedUserService.deleteByEmail(email);
        }
    }

    @Override
    public boolean existsById(UUID id) {
        return userService.existsById(id);
    }

    @Override
    public SystemUserDto systemFindById(UUID id) {
        return userService.systemFindById(id);
    }

    @Override
    public SystemUserDto systemFindByEmail(String email) throws BaseNotFoundException {
        try {
            return userService.systemFindByEmail(email);
        } catch (BaseNotFoundException e) {
            return unconfirmedUserService.systemFindByEmail(email);
        }
    }

    @Override
    public UserResponseDto findById(UUID id) {
        return userService.findById(id);
    }

    @Override
    public UserResponseDto update(UUID id, UserUpdateDto updateDto) {
        SystemUserUpdateDto systemUpdateDto = userService.mapToUpdateDto(updateDto);
        systemUpdateDto.setId(id);
        Set<ConstraintViolation<SystemUserUpdateDto>> bindingResult = validator.validate(systemUpdateDto);
        if(!bindingResult.isEmpty()) {
            throw new ConstraintViolationException(bindingResult);
        }
        return userService.update(systemUpdateDto);
    }

    @Override
    public void recoverPasswordByEmail(RecoveryRequestDto recoveryRequest) {
        if (!userService.existsByEmail(recoveryRequest.resource())) {
            throw new EmailNotFoundException();
        }
        userService.updatePasswordByEmail(recoveryRequest.resource(), recoveryRequest.password());
    }

    @Override
    public void deleteById(UUID id) {
        userService.deleteById(id);
    }

    @Override
    public void deleteByPassword(UUID id, String password, HttpServletRequest request, HttpServletResponse response) {
        userService.deleteByPassword(id, password);
        userDeleteHandler.handle(request, response);
    }
}
