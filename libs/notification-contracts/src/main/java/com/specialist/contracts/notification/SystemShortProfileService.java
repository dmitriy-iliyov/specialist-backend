package com.specialist.contracts.notification;


import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface SystemShortProfileService {
    Map<UUID, SystemShortProfileResponseDto> findAllShortByIdIn(Set<UUID> ids);
}
