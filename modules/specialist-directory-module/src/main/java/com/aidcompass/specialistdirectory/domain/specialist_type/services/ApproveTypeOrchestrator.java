package com.aidcompass.domain.specialist_type.services;

import com.aidcompass.domain.specialist.services.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApproveTypeOrchestrator {

    private final SpecialistService specialistService;
    private final TypeService typeService;

    public void approve(Long id) {
        typeService.approve(id);
        specialistService.updateAllByTypeIdPair(TypeConstants.OTHER_TYPE_ID, id);
    }
}
