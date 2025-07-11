package com.aidcompass.users.detail;

import com.aidcompass.users.detail.models.DetailDto;
import com.aidcompass.users.detail.models.PrivateDetailResponseDto;
import com.aidcompass.core.general.contracts.enums.ServiceType;

import java.util.UUID;

public interface DetailService {
    PrivateDetailResponseDto update(UUID userId, DetailDto dto, ServiceType serviceType);
}
