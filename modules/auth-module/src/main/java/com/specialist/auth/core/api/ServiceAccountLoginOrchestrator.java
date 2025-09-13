package com.specialist.auth.core;

import com.specialist.auth.core.models.ServiceLoginRequest;

import java.util.Map;

public interface ServiceAccountLoginOrchestrator {
    Map<String, String> login(ServiceLoginRequest loginRequest);
}
