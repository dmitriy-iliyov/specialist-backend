package com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases;

import org.springframework.transaction.annotation.Transactional;

public interface TypeApproveOrchestrator {
    @Transactional
    void approve(Long id);
}
