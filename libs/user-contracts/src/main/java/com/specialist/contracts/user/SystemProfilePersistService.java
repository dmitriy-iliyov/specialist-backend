package com.specialist.contracts.user;

import com.specialist.contracts.user.dto.ShortUserCreateDto;

public interface SystemUserPersistService {
    void save(ShortUserCreateDto dto);
}
