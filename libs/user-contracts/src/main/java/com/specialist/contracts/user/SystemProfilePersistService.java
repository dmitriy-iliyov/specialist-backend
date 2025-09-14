package com.specialist.contracts.user;

import com.specialist.contracts.user.dto.ShortProfileCreateDto;

public interface SystemProfilePersistService {
    void save(ShortProfileCreateDto dto);
}
