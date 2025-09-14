package com.specialist.user.services;

import com.specialist.contracts.user.ProfileType;
import com.specialist.user.models.dtos.BasePrivateResponseDto;
import com.specialist.user.models.dtos.UserCreateDto;

public interface ProfilePersistService<C extends UserCreateDto, P extends BasePrivateResponseDto> {
    P save(C dto);
    ProfileType getType();
}
