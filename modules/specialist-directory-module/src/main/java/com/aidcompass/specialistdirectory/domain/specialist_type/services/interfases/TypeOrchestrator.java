package com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases;

import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeUpdateDto;

public interface TypeOrchestrator {
    TypeDto update(TypeUpdateDto dto);
}
