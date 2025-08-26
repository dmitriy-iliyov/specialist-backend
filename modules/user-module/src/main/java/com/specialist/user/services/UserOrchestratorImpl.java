package com.specialist.user.services;

import com.specialist.contracts.auth.SystemAccountFacade;
import com.specialist.user.models.dtos.BaseUserDto;
import com.specialist.user.models.dtos.PrivateUserResponseDto;
import com.specialist.user.models.dtos.UserUpdateDto;
import com.specialist.user.repositories.AvatarStorage;
import jakarta.servlet.http.HttpServletResponse;
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
    private final SystemAccountFacade accountFacade;

    private void validate(BaseUserDto dto) {
        Set<ConstraintViolation<BaseUserDto>> errors = validator.validate(dto);
        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
    }

    private void saveAvatar(BaseUserDto dto) {
        if (dto.getAvatar() != null && !dto.getAvatar().isEmpty()) {
            dto.setAvatarUrl(avatarStorage.save(dto.getAvatar(), dto.getId()));
        }
    }

    @Override
    public PrivateUserResponseDto save(BaseUserDto dto) {
        validate(dto);
        saveAvatar(dto);
        return userService.save(dto);
    }

    @Transactional
    @Override
    public PrivateUserResponseDto update(UserUpdateDto dto) {
        validate(dto);
        saveAvatar(dto);
        return userService.update(dto, accountFacade::updateEmailById);
    }

    @Override
    public String updateAvatar(MultipartFile avatar, UUID id) {
        String avatarUrl = avatarStorage.save(avatar, id);
        userService.updateAvatarUrlById(id, avatarUrl);
        return avatarUrl;
    }

    @Transactional
    @Override
    public void delete(UUID id, UUID refreshTokenId, HttpServletResponse response) {
        userService.deleteById(id);
        accountFacade.deleteById(id, refreshTokenId, response);
        avatarStorage.deleteByUserId(id);
    }
}
