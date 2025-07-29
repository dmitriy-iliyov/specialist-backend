package com.aidcompass.user.services;

import com.aidcompass.user.models.dtos.PrivateUserResponseDto;
import com.aidcompass.user.models.dtos.UserUpdateDto;
import com.aidcompass.user.models.dtos.UserCreateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserOrchestrator {
    PrivateUserResponseDto save(UserCreateDto dto);

    PrivateUserResponseDto update(UserUpdateDto dto);

    String updateAvatar(MultipartFile avatar, UUID id);

    void delete(UUID id);
}
