package com.specialist.user.services;

import com.specialist.contracts.user.UserType;
import com.specialist.user.models.dtos.BasePrivateResponseDto;
import com.specialist.user.models.dtos.UserCreateDto;

public interface UserPersistService<C extends UserCreateDto, P extends BasePrivateResponseDto> {
    P save(C dto);
    UserType getType();
}
