package com.aidcompass.specialistdirectory.domain.type.services.interfaces;

import org.springframework.transaction.annotation.Transactional;

public interface TypeApproveOrchestrator {
    @Transactional
    void approve(Long id);
}
