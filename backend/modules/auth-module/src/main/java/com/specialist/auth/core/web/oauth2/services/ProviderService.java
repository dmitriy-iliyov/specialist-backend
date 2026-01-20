package com.specialist.auth.core.web.oauth2.services;

import java.util.List;
import java.util.Map;

public interface ProviderService {
    List<String> findAll();

    Map<String, String> findAllPaths();
}
