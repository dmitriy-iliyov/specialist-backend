package com.aidcompass.user.services;

import com.aidcompass.contracts.auth.SystemAccountService;
import com.aidcompass.user.models.dtos.UserCreateDto;
import com.aidcompass.user.models.dtos.UserUpdateDto;
import com.aidcompass.user.models.dtos.PrivateUserResponseDto;
import com.aidcompass.user.repositories.AvatarStorage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserOrchestratorImpl implements UserOrchestrator {

    private final UserService userService;
    private final AvatarStorage avatarStorage;
    private final Validator validator;
    private final SystemAccountService accountService;


    @Override
    public PrivateUserResponseDto save(UserCreateDto dto) {
        if (!dto.getAvatar().isEmpty()) {
            dto.setAvatarUrl(avatarStorage.save(dto.getAvatar(), dto.getId()));
        }
        return userService.save(dto);
    }

    @Transactional
    @Override
    public PrivateUserResponseDto update(UserUpdateDto dto) {
        Set<ConstraintViolation<UserUpdateDto>> errors = validator.validate(dto);
        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
        if (!dto.getAvatar().isEmpty()) {
            dto.setAvatarUrl(avatarStorage.save(dto.getAvatar(), dto.getId()));
        }
        PrivateUserResponseDto responseDto = userService.update(dto);
        accountService.updateEmailById(responseDto.getId(), responseDto.getEmail());
        return responseDto;
    }

    @Override
    public String updateAvatar(MultipartFile avatar, UUID id) {
        String avatarUrl = avatarStorage.save(avatar, id);
        userService.updateAvatarUrlById(id, avatarUrl);
        return avatarUrl;
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        userService.deleteById(id);
        accountService.deleteById(id);
        avatarStorage.deleteByUserId(id);
    }
}
