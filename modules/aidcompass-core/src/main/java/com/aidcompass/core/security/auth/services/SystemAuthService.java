package com.aidcompass.core.security.auth.services;

import com.aidcompass.core.security.auth.dto.ServiceAuthRequest;

import java.util.Map;

public interface SystemAuthService {
    Map<String, String> login(ServiceAuthRequest requestDto);

    Map<String, String> generateToken(String serviceName, Integer daysTtl);
}
