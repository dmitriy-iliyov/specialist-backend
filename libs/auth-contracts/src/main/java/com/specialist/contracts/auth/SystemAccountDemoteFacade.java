package com.specialist.contracts.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SystemAccountDemoteFacade {
    void demote(DemoteRequest request);
}
