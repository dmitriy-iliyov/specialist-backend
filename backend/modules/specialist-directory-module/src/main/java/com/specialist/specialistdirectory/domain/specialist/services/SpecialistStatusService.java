package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;

import java.util.UUID;

public interface SpecialistStatusService {
    void approve(UUID id, UUID approverId, ApproverType approverType);
    void suspend(UUID id);
}
