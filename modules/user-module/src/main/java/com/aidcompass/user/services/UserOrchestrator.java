package com.aidcompass.user.services;

import com.aidcompass.user.models.dto.PrivateUserResponseDto;
import com.aidcompass.user.models.dto.UserUpdateDto;
import com.aidcompass.user.models.dto.UserCreateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserOrchestrator {
    PrivateUserResponseDto save(UserCreateDto dto);

    PrivateUserResponseDto update(UserUpdateDto dto);

    String updateAvatar(MultipartFile avatar, UUID id);

    void delete(UUID id);
}
