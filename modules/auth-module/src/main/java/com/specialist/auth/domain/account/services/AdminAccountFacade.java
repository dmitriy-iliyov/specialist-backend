package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.dtos.DemodeRequest;
import com.specialist.auth.domain.account.models.dtos.DisableRequest;
import com.specialist.auth.domain.account.models.dtos.LockRequest;

import java.util.UUID;

public interface AdminAccountOrchestrator {
    void disableById(UUID id, DisableRequest request);

    void lockById(UUID id, LockRequest request);

    void demoteById(DemodeRequest request);
}
