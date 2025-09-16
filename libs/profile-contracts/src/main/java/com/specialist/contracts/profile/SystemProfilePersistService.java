package com.specialist.contracts.profile;

import com.specialist.contracts.profile.dto.ShortProfileCreateDto;

public interface SystemProfilePersistService {
    void save(ShortProfileCreateDto dto);
}
