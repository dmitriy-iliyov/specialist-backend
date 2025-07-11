package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;

import java.util.UUID;

public interface SpecialistCountService {
    long countAll();

    long countByFilter(SpecialistFilter filter);

    long countByCreatorId(UUID creatorId);

    long countByCreatorIdAndFilter(UUID creatorId, ExtendedSpecialistFilter filter);
}
