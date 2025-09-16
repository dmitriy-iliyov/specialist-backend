package com.specialist.auth.core.api;

import java.util.Map;

public interface ServiceAccountLoginService {
    Map<String, String> login(ServiceLoginRequest loginRequest);
}
