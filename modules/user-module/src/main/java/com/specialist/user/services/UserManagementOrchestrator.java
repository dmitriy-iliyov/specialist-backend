package com.specialist.user.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.specialist.contracts.user.UserType;
import com.specialist.user.models.dtos.BasePrivateResponseDto;
import com.specialist.user.models.dtos.UpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserManagementOrchestrator {
    BasePrivateResponseDto update(UpdateRequest request) throws JsonProcessingException;
    String updateAvatar(UUID id, UserType type, MultipartFile avatar);
}
