package com.specialist.auth.core.api;

import java.util.Map;

public interface ServiceAccountLoginOrchestrator {
    Map<String, String> login(ServiceLoginRequest loginRequest);
}
