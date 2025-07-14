package com.aidcompass.specialistdirectory.domain.specialist_type.services;

import com.aidcompass.specialistdirectory.domain.specialist.services.interfaces.SpecialistService;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeApproveOrchestrator;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TypeApproveOrchestratorImpl implements TypeApproveOrchestrator {

    private final SpecialistService specialistService;
    private final TypeService typeService;

    @Transactional
    @Override
    public void approve(Long id) {
        typeService.approve(id);
        specialistService.updateAllByTypeIdPair(TypeConstants.OTHER_TYPE_ID, id);
    }
}
