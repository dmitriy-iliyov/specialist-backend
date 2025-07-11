package com.aidcompass.core.security.domain.user.services;

import com.aidcompass.core.security.domain.user.models.dto.SystemUserDto;
import com.aidcompass.core.security.domain.user.models.dto.UserRegistrationDto;

import java.util.UUID;

public interface UnconfirmedUserService {

    void save(UUID id, UserRegistrationDto dto);

    boolean existsByEmail(String email);

    SystemUserDto systemFindByEmail(String email, Long emailId);

    SystemUserDto systemFindByEmail(String email);

    void deleteByEmail(String email);
}
