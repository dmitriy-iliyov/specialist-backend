package com.aidcompass.specialistdirectory.domain.type.services;

import com.aidcompass.specialistdirectory.domain.specialist.services.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApproveTypeOrchestratorImpl implements ApproveTypeOrchestrator {

    private final SpecialistService specialistService;
    private final TypeService typeService;

    @Transactional
    @Override
    public void approve(Long id) {
        typeService.approve(id);
        specialistService.updateAllByTypeIdPair(TypeConstants.OTHER_TYPE_ID, id);
    }
}
