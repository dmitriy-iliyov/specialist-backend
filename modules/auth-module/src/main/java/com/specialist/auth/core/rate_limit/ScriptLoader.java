package com.specialist.auth.core.rate_limit;

import jakarta.annotation.PostConstruct;

public interface ScriptLoader {
    @PostConstruct
    void loadScript();

    String getScriptSha();
}
