package com.aidcompass.core.security.domain.user.services;


import com.aidcompass.core.security.auth.dto.RecoveryRequestDto;
import com.aidcompass.core.security.domain.user.models.dto.UserResponseDto;
import com.aidcompass.core.security.domain.user.models.dto.SystemUserDto;
import com.aidcompass.core.security.domain.user.models.dto.UserRegistrationDto;
import com.aidcompass.core.security.domain.user.models.dto.UserUpdateDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface UserOrchestrator {

    void save(UserRegistrationDto dto);

    void confirmByEmail(String email);

    SystemUserDto systemFindByEmail(String email);

    boolean existsById(UUID id);

    SystemUserDto systemFindById(UUID id);

    UserResponseDto findById(UUID id);

    UserResponseDto update(UUID id, UserUpdateDto updateDto);

    void recoverPasswordByEmail(RecoveryRequestDto recoveryRequest);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password, HttpServletRequest request, HttpServletResponse response);
}
