package com.specialist.user.services;

import com.specialist.contracts.user.UserType;

import java.util.UUID;

public interface UserDeleteService {
    void deleteById(UUID id);
    UserType getType();
}
