package com.specialist.auth.core;

import com.specialist.auth.core.models.ServiceLoginRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface ServiceAccountAuthService {
    Map<String, String> login(ServiceLoginRequest requestDto, HttpServletRequest request);
}
